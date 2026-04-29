import strawberry
from typing import List, Optional
from src.storage.in_memory_db import developers, games, notifications, Notification
from strawberry.exceptions import GraphQLError

# Developer GraphQL Type
@strawberry.type
class DeveloperType:
    id: int
    name: str
    country: str

    @strawberry.field
    def games(self) -> List["GameType"]:
        return [g for g in games if g.developer_id == self.id]

# Game GraphQL Type
@strawberry.type
class GameType:
    id: int
    title: str
    genre: str
    release_year: int
    developer_id: int

    @strawberry.field
    def developer(self) -> Optional[DeveloperType]:
        dev = next((d for d in developers if d.id == self.developer_id), None)
        if dev:
            return DeveloperType(**dev.model_dump())
        raise GraphQLError(f"Developer with id {self.developer_id} not found")

# Notification GraphQL Type
@strawberry.type
class NotificationType:
    id: int
    message: str
    is_read: bool