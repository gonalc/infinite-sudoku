# Update Project Status

Update task status and project progress in the Infinite Sudoku Notion workspace.

## Notion References

- **Task Tracker Database:** `49aaf0c0fc06465cbc8e38e5d62c60ae`
- **Task Tracker Data Source:** `d6f299ab-6597-4301-b5dd-9346720aef8d`
- **Main Project Page:** `3697502923b380a58c37dd51252f5bf3`

## Status Values

- `Backlog` - Not yet planned
- `To Do` - Ready to start
- `In Progress` - Currently being worked on
- `Review` - Awaiting review
- `Done` - Completed
- `Blocked` - Blocked by dependency

## Priority Values

- `P0 - Critical` - Must be done first
- `P1 - High` - Important
- `P2 - Medium` - Standard priority
- `P3 - Low` - Nice to have

## Milestone Values

- `M1 - Project Setup`
- `M2 - Puzzle Engine`
- `M3 - Persistence`
- `M4 - Game Loop`
- `M5 - Daily & Stats`
- `M6 - Polish`
- `M7 - Release`

## Instructions

When updating project status:

1. **Search for the task** using `notion-search` with query type `internal`
2. **Fetch task details** if needed using `notion-fetch`
3. **Update the task** using `notion-update-page` with command `update_properties`

### To mark a task as complete:
```json
{
  "page_id": "<task_id>",
  "command": "update_properties",
  "properties": {
    "Status": "Done"
  }
}
```

### To start working on a task:
```json
{
  "page_id": "<task_id>",
  "command": "update_properties",
  "properties": {
    "Status": "In Progress"
  }
}
```

### To create a new task:
Use `notion-create-pages` with parent `data_source_id: d6f299ab-6597-4301-b5dd-9346720aef8d`

### To update milestone status on main page:
After completing all tasks in a milestone, update the main project page's milestone table.

## User Argument

$ARGUMENTS

### Examples:
- `/update-status mark "Initialize KMP project" as done`
- `/update-status start working on "Set up Gradle"`
- `/update-status add task "Fix grid rendering bug" to M1`
- `/update-status show current progress`

If no argument provided, show current task status summary.
