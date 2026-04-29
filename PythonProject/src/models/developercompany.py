from pydantic import BaseModel

class DeveloperCompany(BaseModel):
    id: int
    name: str
    country: str