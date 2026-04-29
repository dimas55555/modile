from fastapi import FastAPI
from strawberry.fastapi import GraphQLRouter
from src.graphql.schema import schema

app = FastAPI(title="Video Game Library GraphQL API with Notifications")

graphql_app = GraphQLRouter(schema)

app.include_router(graphql_app, prefix="/graphql")