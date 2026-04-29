import strawberry
from typing import Optional
from strawberry.exceptions import GraphQLError
from src.storage.in_memory_db import games, developers, create_notification, notifications
from src.models.game import Game
from src.models.developercompany import DeveloperCompany
from src.graphql.types import GameType, DeveloperType, NotificationType

@strawberry.type
class Mutation:

    # ------------------- Games -------------------
    @strawberry.mutation
    def create_game(self, title: str, genre: str, release_year: int, developer_id: int) -> GameType:
        new_id = max([g.id for g in games], default=0) + 1
        game = Game(id=new_id, title=title, genre=genre, release_year=release_year, developer_id=developer_id)
        games.append(game)

        create_notification(f"New game created: {title}")
        return GameType(**game.model_dump())

    @strawberry.mutation
    def update_game(self, id: int, title: Optional[str] = None, genre: Optional[str] = None) -> Optional[GameType]:
        game = next((g for g in games if g.id == id), None)
        if not game:
            raise GraphQLError(f"Game with id {id} not found")

        if title:
            game.title = title
        if genre:
            game.genre = genre

        create_notification(f"Game updated: {game.title}")
        return GameType(**game.model_dump())

    @strawberry.mutation
    def delete_game(self, id: int) -> int:
        game = next((g for g in games if g.id == id), None)
        if not game:
            raise GraphQLError(f"Game with id {id} not found")

        games.remove(game)
        create_notification(f"Game deleted: {game.title}")
        return game.id

    # ------------------- Developers -------------------
    @strawberry.mutation
    def create_developer(self, name: str, country: str) -> DeveloperType:
        new_id = max([d.id for d in developers], default=0) + 1
        developer = DeveloperCompany(id=new_id, name=name, country=country)
        developers.append(developer)

        create_notification(f"New developer created: {name}")
        return DeveloperType(**developer.model_dump())

    @strawberry.mutation
    def update_developer(self, id: int, name: Optional[str] = None, country: Optional[str] = None) -> Optional[DeveloperType]:
        developer = next((d for d in developers if d.id == id), None)
        if not developer:
            raise GraphQLError(f"Developer with id {id} not found")

        if name:
            developer.name = name
        if country:
            developer.country = country

        create_notification(f"Developer updated: {developer.name}")
        return DeveloperType(**developer.model_dump())

    @strawberry.mutation
    def delete_developer(self, id: int) -> int:
        developer = next((d for d in developers if d.id == id), None)
        if not developer:
            raise GraphQLError(f"Developer with id {id} not found")

        developers.remove(developer)
        create_notification(f"Developer deleted: {developer.name}")
        return developer.id

    # ------------------- Notifications -------------------
    @strawberry.mutation
    def mark_notification_as_read(self, id: int) -> NotificationType:
        n = next((n for n in notifications if n.id == id), None)
        if not n:
            raise GraphQLError("Notification not found")
        n.is_read = True
        return NotificationType(**n.model_dump())