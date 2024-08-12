# Notepad Application

## Overview

This project is a Notepad application developed in Java for our university Programming Data Structures and Algorithms module. It demonstrates the practical use of data structures in a real-world application. The main features of the Notepad include:

- **Undo/Redo functionality** using Stack data structure.
- **Line Finder** using ArrayList.
- **Word Finder** using HashMap.

## Features

### Undo/Redo Functionality
The Notepad application supports undo and redo operations to help users revert and reapply their changes. This is achieved using two stacks:
- **Undo Stack**: Stores the states of the text for undo operations.
- **Redo Stack**: Stores the states for redo operations when an undo action is performed.

### Line Finder
The Line Finder feature allows users to quickly navigate to a specific line in the document. This is implemented using an ArrayList where each element corresponds to a line in the text.

### Word Finder
The Word Finder feature helps users search for specific words in the document. This functionality is implemented using a HashMap where the keys are the words and the values are the positions of those words in the text.

## Team Members

- **Name : A S UDAYANGA**
-- **Student ID**: COHNDSE241F-089

- **Name : W T M DIAS**
-- **Student ID**: COHNDSE241F-087

## Usage

- Use the text area to type your notes.
- Use the Edit menu to access Undo and Redo features.
- Use the Find menu to navigate to specific lines or search for words.
