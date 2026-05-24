# Update Documentation

Update the Infinite Sudoku project documentation in Notion.

## Notion Page References

- **Main Project Page:** `3697502923b380a58c37dd51252f5bf3`
- **Product Requirements:** `3697502923b3813eb79fda15e2833112`
- **Architecture Overview:** `3697502923b381288100fbd8408b9a64`
- **Tech Stack:** `3697502923b381698586dd79801f1e91`
- **Domain Models:** `3697502923b381949f6ddd6c98afe814`
- **Implementation Roadmap:** `3697502923b3814fa354fff40f6695d4`
- **Testing Strategy:** `3697502923b38156af44cad96d1b15ec`
- **Technical Risks:** `3697502923b381558595e56049ae656d`

## Instructions

When the user asks to update documentation, follow these steps:

1. **Identify which document(s) need updating** based on the user's request
2. **Fetch the current content** of the relevant Notion page(s) using `notion-fetch`
3. **Determine the changes needed** based on:
   - Recent code changes in the project
   - User's specific instructions
   - New decisions or architectural changes
4. **Update the Notion page(s)** using `notion-update-page` with the appropriate command:
   - Use `update_content` for surgical changes (search and replace)
   - Use `replace_content` for major rewrites
   - Use `insert_content` for adding new sections

## Common Update Scenarios

### After implementing a feature
- Update the Implementation Roadmap to mark tasks complete
- Update Architecture Overview if patterns changed
- Update Domain Models if new entities were added

### After making architectural decisions
- Update Architecture Overview with new diagrams/patterns
- Update Tech Stack if dependencies changed
- Update Technical Risks if new risks identified

### After adding tests
- Update Testing Strategy with new test coverage
- Add test examples if patterns changed

## User Argument

$ARGUMENTS

If no argument provided, ask the user what documentation they want to update.
