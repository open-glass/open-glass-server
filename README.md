# Open Glass Server

Backend of Open Glass. This component is running on the gaming computer and offering a REST API which allows
apps and webapps to send key presses to the game.

## Supported Games
* Star Citizen (basic)

## Adding new Games
1. Add the game to the Games enum in `de.seine_eloquenz.open_glass.server.Games`.
2. Follow the instructions in the [app component](https://github.com/open-glass/open-glass-app) to extend the flutter app
or build your own frontend to interface with the backend.