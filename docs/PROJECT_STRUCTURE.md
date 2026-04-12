# Project Structure Guide

## Current Structure Standards
- Keep all `.java` source files in `src/`.
- Keep generated `.class` files out of `src/`.
- Compile into `bin/` using `javac -d bin src\\*.java`.
- Keep project docs in `docs/`.

## Naming and Organization
- Use PascalCase for class filenames (example: `AccountManager.java`).
- Keep one primary class per file where practical.
- Keep entry points explicit:
  - `Main.java` for CLI
  - `MainGUI.java` for Swing GUI

## Recommended Workflow
1. Open terminal at project root.
2. Compile all source to `bin`.
3. Run desired entry point using `-cp bin`.
4. Commit only source and docs, not build output.

## Future Enhancement Suggestions
- Move account persistence file to dedicated `data/` directory.
- Add package namespaces (example: `com.injuryassist.model`, `com.injuryassist.service`, `com.injuryassist.ui`).
- Add lightweight tests for account validation and scheduling logic.
