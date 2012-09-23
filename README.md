# Jilch [![Build Status](https://secure.travis-ci.org/mpenet/jilch.png?branch=master)](http://travis-ci.org/mpenet/jilch)

Clojure ZeroMQ Library using jeromq, it is a direct port of zilch.

## But why ?

Because [jeromq](https://github.com/miniway/jeromq) made it easy.

## Using

If you use zilch you just need to change your imports and it
should work.

It also includes a few enhancements from the original, such as more complete
type hinting, extensibility, a few functions from the 0mq docs and
[Storm](https://github.com/nathanmarz/storm), and runs on clj 1.4 by default.

## Installation

Put this in your project.clj, then `lein deps`, and you are good to go, no more messin'
with native deps.

```clojure
[cc.qbits/jilch "0.1.0-SNAPSHOT"]
```
## License

Distributed under the Eclipse Public License, the same as Clojure.
