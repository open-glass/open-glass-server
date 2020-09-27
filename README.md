# Open Glass Server
[![Build Status](https://travis-ci.com/open-glass/open-glass-server.svg?branch=master)](https://travis-ci.com/github/open-glass/open-glass-server)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/f4bd561c3cea495fb4aab7ed2d41b884)](https://www.codacy.com/gh/open-glass/open-glass-server/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=open-glass/open-glass-server&amp;utm_campaign=Badge_Grade)

Backend of Open Glass. This component is running on the gaming computer and offering a REST API which allows
apps and webapps to send key presses to the game.

## Supported Games
* Star Citizen (basic)

## Adding new Games
1. Add the game to the Games enum in `de.seine_eloquenz.open_glass.server.Games`.
2. Follow the instructions in the [app component](https://github.com/open-glass/open-glass-app) to extend the flutter app
or build your own frontend to interface with the backend.