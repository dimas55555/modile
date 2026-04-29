# 🎮 Video Game Library GraphQL API

Простий демонстраційний проєкт GraphQL API для управління бібліотекою відеоігор. Реалізовано з використанням Python, FastAPI та Strawberry GraphQL без використання бази даних (дані зберігаються в пам’яті - in-memory-storage).

---

## 📌 Опис проєкту

Даний застосунок реалізує GraphQL API, який дозволяє працювати з двома основними сутностями (Nodes):

* 🎮 Відеоігри (Games)
* 🏢 Компанія Розробник (Developers)

Проєкт демонструє основні можливості GraphQL:

* виконання запитів (queries)
* виконання мутацій (mutations)
* вкладені запити (nested queries)
* зв’язки між сутностями
* базову обробку помилок

---

## ⚙️ Технології

* Python 3.x
* FastAPI
* Strawberry GraphQL
* Pydantic
* Uvicorn

---

## 🔍 Основні можливості API

### 📥 Query (Запити)

Отримання списку ігор:

```graphql
query {
  games {
    id
    title
    genre
    releaseYear
  }
}
```

Отримання ігри з розробником:

```graphql
query {
  game(id: 1) {
    title
    developer {
      name
      country
    }
  }
}
```

Отримання розробників з їхніми іграми:

```graphql
query {
  developers {
    name
    games {
      title
    }
  }
}
```

---

### ✏️ Mutation (Мутації)

Створення гри:

```graphql
mutation {
  createGame(
    title: "Red Dead Redemption 2"
    genre: "Action"
    releaseYear: 2018
    developerId: 2
  ) {
    id
    title
  }
}
```

Оновлення гри:

```graphql
mutation {
  updateGame(
    id: 1
    title: "The Witcher 3: Wild Hunt"
  ) {
    id
    title
  }
}
```

Видалення гри:

```graphql
mutation {
  deleteGame(id: 3)
}
```
---

Відповідні мутації наявні і для створення, оновлення та видалення компанії розробка (createDeveloper, updateDeveloper, deleteDeveloper).

---

## 🔗 Вкладені запити (Nested Queries)

GraphQL дозволяє отримувати пов’язані дані одним запитом:

```graphql
query {
  games {
    title
    developer {
      name
    }
  }
}
```

Зв’язок між сутностями реалізований через резолвери:

* `Game → Developer`
* `Developer → Games`

---

## ⚠️ Обробка помилок

У проєкті використовується базова обробка помилок через GraphQL:

* при відсутності ресурсу генерується помилка
* відповідь містить поле `errors`

Приклад:

```json
{
  "data": {
    "game": null
  },
  "errors": [
    {
      "message": "Game with id not found"
    }
  ]
}
```

---