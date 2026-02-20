# AI Tool Usage Log (iP.AI)

## Tools Used

- GitHub Copilot (IDE: IntelliJ IDEA)
- ChatGPT (model: GPT-5.2 Thinking)

## Log

### Week 5 — A-CheckStyle / A-CodeQuality

**What I used AI for**

- Refactoring long methods in `Parser` into smaller helpers.
- Suggesting JavaDoc blocks for new helper methods.
- Proposing small utility methods to reduce repeated printing code.

**What I kept manual**

- Preserved exact output formatting and exception messages to match existing behavior/tests.
- Finalized method names/signatures to fit the existing codebase structure.

**What worked well**

- AI-generated refactor outlines were quick to adapt.
- JavaDocs were mostly correct with minor edits.

**What didn’t work / limitations**

- Needed careful review to avoid changing CLI output and error messages.
- AI sometimes suggested creating new abstractions that were unnecessary.

**Estimated time saved**

- ~X minutes/hours (rough estimate)

**Notes**

- Always ran `./gradlew clean test` after changes.
- Ran `./gradlew checkstyleMain` to ensure style compliance.
