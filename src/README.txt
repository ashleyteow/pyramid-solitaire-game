From homework 2 to homework3 --> 
- Changed the way I handled exceptions in the model. Eg. I was throwing IllegalStateExceptions when they should have been IllegalArgumentExceptions

- getDeck() now returns a valid deck for game play -- was confused about this method before and thought of it as a getter method. 
	- the deck given in startGame remains unchanged after building the pyramid because I changed it to use a copy of the deck as opposed to mutating the given list

- discardDraw() also removes the card completely instead of adding it back to the stock pile (again, misunderstanding on my part)

- getScore throws an illegalStateException when the game has not started

- getDrawCards throws an illegalStateException when the game has not started

- private field deckOfCards can be known as the game's stock pile and is mutated throughout game play