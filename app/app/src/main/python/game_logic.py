class TicTacToe:
    def __init__(self):
        self.board = [None] * 9
        self.current_player = "X"
        self.winner = None

    def make_move(self, position):
        if self.board[position] is None and self.winner is None:
            self.board[position] = self.current_player
            if self.check_winner():
                self.winner = self.current_player
            self.current_player = "O" if self.current_player == "X" else "X"
            return True
        return False

    def check_winner(self):
        win_positions = [
            [0, 1, 2], [3, 4, 5], [6, 7, 8],  # Rows
            [0, 3, 6], [1, 4, 7], [2, 5, 8],  # Columns
            [0, 4, 8], [2, 4, 6]  # Diagonals
        ]
        for positions in win_positions:
            if self.board[positions[0]] == self.board[positions[1]] == self.board[positions[2]] and self.board[positions[0]] is not None:
                return True
        return False

    def reset(self):
        self.board = [None] * 9
        self.current_player = "X"
        self.winner = None
