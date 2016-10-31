# Example Marvel Scala

A Scala port of the JavaScript [Example Marvel App](https://github.com/nicolashery/example-marvel-app).

## Quick start

Make sure you have [SBT](https://github.com/sbt/sbt) installed. The recommended way is to install [sbt-extras](https://github.com/paulp/sbt-extras).

Clone this repository, and then create an `.env` file in the root directory containing necessary configuration variables:

```
export PORT=3000
export MARVEL_PUBLIC_KEY=your_public_key
export MARVEL_PRIVATE_KEY=your_private_key
```

Run the app with:

```bash
$ source .env
$ sbt run
```

For development, launch the SBT REPL:

```bash
$ source .env
$ sbt
```

Inside the REPL, start the server in "triggered restart" mode (using [sbt-revolver](https://github.com/spray/sbt-revolver)) with:

```
> ~re-start
```

To stop the server, press `ENTER` to return to the prompt and type the command:

```
> re-stop
```

