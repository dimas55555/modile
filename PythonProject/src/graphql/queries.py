import strawberry
from typing import List, Optional
from src.storage.in_memory_db import games, developers, notifications
from src.graphql.types import GameType, DeveloperType, NotificationType
from strawberry.exceptions import GraphQLError

@strawberry.type
class Query:

    # Ігри
    @strawberry.field
    def games(self) -> List[GameType]:
        return [GameType(**g.model_dump()) for g in games]

    @strawberry.field
    def game(self, id: int) -> Optional[GameType]:
        g = next((g for g in games if g.id == id), None)
        if not g:
            raise GraphQLError(f"Game with id {id} not found")
        return GameType(**g.model_dump())

    # Розробники
    @strawberry.field
    def developers(self) -> List[DeveloperType]:
        return [DeveloperType(**d.model_dump()) for d in developers]

    @strawberry.field
    def developer(self, id: int) -> Optional[DeveloperType]:
        d = next((d for d in developers if d.id == id), None)
        if not d:
            raise GraphQLError(f"Developer with id {id} not found")
        return DeveloperType(**d.model_dump())

    # Нотифікації
    @strawberry.field
    def notifications(self) -> List[NotificationType]:
        return [NotificationType(**n.model_dump()) for n in notifications]