# Personal Planner Backend

## Developer: Brooskiey Bullet

## GitHub Link: https://github.com/Brooskiey/personal-planner-backend

## Endpoints
- tasks
  - GET `/tasks`
    - Get all tasks
  - GET `/tasks/day/{date}`
    - Get all tasks by date
  - GET `/tasks/{task_id}`
    - Get task by id
  - GET `/tasks/week/{date}`
    - Get the important appointments for the week
  - GET `/tasks/type/{type_id}`
    - Get tasks by type
  - POST `/tasks`
    - Body: 
    ```json
    { 
    "task_id":"number",
    "name":"string",
    "task_type_name":"string",
    "status_name":"string",
    "recurrence": "string",
    "last_date_occurred": "Date",
    "date_initiated":"Date",
    "date_completed":"Date",
    "is_complete":"boolean"
    }
    ```
  
## Database
- TASK
  - id:number (Primary Key)
  - name:string
  - type_id:number (Foreign Key)
  - status_id:number (Foreign Key)
  - recurr_id:number (Foreign Key)
  - last_date_occurred:Date
  - date_initiated:Date
  - date_completed:Date
  - is_complete:boolean
- TASK_TYPE
  - id:number (Primary Key)
  - name:string
- TASK_STATUS
  - id:number (Primary Key)
  - name:string
- RECURR_TASK
  - id:number (Primary Key)
  - recurrence:string
  - last_date:Date
