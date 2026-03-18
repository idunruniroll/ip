# Chad User Guide

Chad is a chatbot that helps you manage **tasks** and **notes** in one place.

---

## Quick Start

1. Download the app [here](https://github.com/idunruniroll/ip/releases/download/v1.2/chad.jar)
2. Open a terminal where the app is stored.
3. Type 'java -jar chad.jar' into the terminal to launch the app.
4. Type a command into the input box at the bottom.
5. Press **Enter** or click **Send**.
6. Chad replies in the chat window.

Tip: Type `help` anytime to see the command list.

---

## Command Format

- Words in `UPPER_CASE` are placeholders you replace.
- Commands are **case-insensitive** (e.g., `LiSt` works).
- Indexes are **1-based** (first item is `1`).

Example:

- `mark 2` marks the **2nd** task in the list.

---

## Task Features

### List tasks

Shows all tasks.

**Command:** `list`

---

### Add a todo

Adds a todo task.

**Command:** `todo DESCRIPTION`

**Example:**  
`todo read chapter 5`

---

### Add a deadline

Adds a deadline task with a due date.

**Command:** `deadline DESCRIPTION /by YYYY-MM-DD`

**Example:**  
`deadline submit report /by 2026-02-20`

---

### Add an event

Adds an event with a start and end date.

**Command:** `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD`

**Example:**  
`event hackathon /from 2026-03-01 /to 2026-03-02`

---

### Mark a task as done

**Command:** `mark INDEX`

**Example:**  
`mark 1`

---

### Unmark a task

**Command:** `unmark INDEX`

**Example:**  
`unmark 1`

---

### Delete a task

Deletes a task by its index.

**Command:** `delete INDEX`

**Example:**  
`delete 3`

---

### Find tasks

Finds tasks containing a keyword in the description.

**Command:** `find KEYWORD`

**Example:**  
`find report`

---

## Note Features

### Add a note

Adds a note with the given text.

**Command:** `note add TEXT`

**Example:**  
`note add remind prof about resubmission email`

---

### List notes

Lists all notes.

**Command:** `note list`

---

### Delete a note

Deletes a note by index.

**Command:** `note delete INDEX`

**Example:**  
`note delete 1`

---

### Find notes

Finds notes containing a keyword.

**Command:** `note find KEYWORD`

**Example:**  
`note find prof`

---

## Help

Shows all available commands.

**Command:** `help`

---

## Exit

Closes the application window.

**Command:** `bye`

---

## FAQ / Troubleshooting

### “Nothing happens when I type”

Make sure you press **Enter** or click **Send**.

### “I got an index error”

- Indexes start from **1**.
- Use `list` (or `note list`) to check the valid indexes.

### “My date format is rejected”

Use `YYYY-MM-DD` (e.g., `2026-02-18`).

---

## Command Summary

Tasks:

- `list`
- `todo DESCRIPTION`
- `deadline DESCRIPTION /by YYYY-MM-DD`
- `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD`
- `mark INDEX`
- `unmark INDEX`
- `delete INDEX`
- `find KEYWORD`

Notes:

- `note add TEXT`
- `note list`
- `note delete INDEX`
- `note find KEYWORD`

General:

- `help`
- `bye`
