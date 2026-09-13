// To support block comments, update the '/' case in scanToken().
// If the next character is '*', call blockComment().
// Otherwise, add a SLASH token as before.
case '/':
  if (match('/')) {
    // A line comment continues until the end of the line.
    while (peek() != '\n' && !isAtEnd()) advance();
  } else if (match('*')) {
    blockComment();
  } else {
    addToken(SLASH);
  }
  break;

// Place this method outside scanToken(), but still inside the Scanner class:

// Scans a C-style block comment until the matching closing marker.
// The nesting variable tracks how many block comments are open.
// Each opening marker increases nesting, while each closing marker
// decreases it. Newlines are counted for accurate error messages.
private void blockComment() {
  int nesting = 1;

  while (nesting > 0 && !isAtEnd()) {
    if (peek() == '/' && peekNext() == '*') {
      advance();
      advance();
      nesting++;
    } else if (peek() == '*' && peekNext() == '/') {
      advance();
      advance();
      nesting--;
    } else {
      if (peek() == '\n') line++;
      advance();
    }
  }

  if (nesting > 0) {
    Lox.error(line, "Unterminated block comment.");
  }
}
