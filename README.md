# Jilch

Clojure ZeroMQ Library using jeromq, it is a direct port of zilch.

## But why ?

Wanted to see if I could port zilch to jeromq in less than 5 minutes.
It was that easy (well, jilch is that good).

It also includes a few enhancements from the original, such as more complete
type hinting, extensibility, a few functions from the 0mq docs and
[Storm](https://github.com/nathanmarz/storm), and runs on clj 1.4 by default.

## Installation

Put this in your project.clj, then `lein deps`, and you are good to go, no more messin'
with native deps.

```clojure
[jilch "0.1.0-SNAPSHOT"]
```
## License

Distributed under the Eclipse Public License, the same as Clojure.
