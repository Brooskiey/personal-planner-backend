# Task Module

## Developer: Brooskiey Bullet

## GitHub Link: https://github.com/Brooskiey/personal-planner-backend

## Endpoints
- taskEntities
    - GET `/taskEntities`
        - Get all taskEntities
    - GET `/taskEntities/day/{date}`
        - Get all taskEntities by date
    - GET `/taskEntities/{task_id}`
        - Get task by id
    - GET `/taskEntities/week/{date}`
        - Get the important appointments for the week
        - Week starts on Monday
    - GET `/taskEntities/type/{type_name}`
        - Get taskEntities by type
    - POST `/taskEntities`
        - Body:
      ```json
      { 
      "task_id":"number",
      "name":"string",
      "task_type": {
        "id": "number",
        "name": "string"
      },
      "task_status": {
        "id": "number",
        "name": "string"
      },
      "task_recurrence":{ 
        "id": "number",
        "category":"string",
        "recurrence": "string",
        "last_date": "LocalDate"
      },
      "date_initiated":"LocalDate",
      "date_completed":"LocalDate",
      "is_complete":"boolean"
      }
      ```
    - PUT `/taskEntities`
        - Body:
      ```json
      { 
      "task_id":"number",
      "name":"string",
      "task_type": {
        "id": "number",
        "name": "string"
      },
      "task_status": {
        "id": "number",
        "name": "string"
      },
      "task_recurrence":{ 
        "id": "number",
        "category":"string",
        "recurrence": "string",
        "last_date": "LocalDate"
      },
      "date_initiated":"LocalDate",
      "date_completed":"LocalDate",
      "is_complete":"boolean"
      }
      ```
    - PUT `/taskEntities/complete/{id}`
        - Complete the task
    - DELETE `/taskEntities/{id}`
        - Delete the task

## Database
- TASK
  - task_id:number (Primary Key)
  - name:string
  - type_id:number (Foreign Key)
  - status_id:number (Foreign Key)
  - recurr_id:number (Foreign Key)
  - date_initiated:LocalDate
  - date_completed:LocalDate
  - is_complete:boolean
- TASK_TYPE
  - type_id:number (Primary Key)
  - name:string
- TASK_STATUS
  - status_id:number (Primary Key)
  - name:string
- RECURR_TASK
  - recurr_id:number (Primary Key)
  - category_id:number (Foreign Key)
  - recurrence:string
  - last_date:LocalDate
- CATEGORY
  - category_id:number (Primary Key)
  - name:string
