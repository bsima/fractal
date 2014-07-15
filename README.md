# fractal

An update of @nakkaya's [blog post](http://nakkaya.com/2009/09/29/fractals-in-clojure-mandelbrot-fractal/) on how to generate fractals in Clojure.

Right now, running `lein run` just generates a standard (but still cool) fractal. In the future, this will be a library for creating fractals in Clojure and ClojureScript.

## Installation

```bash
git clone https://github.com/bsima/fractal.git
cd fractal
lein run
```

## To do

* make the time-fractal work
* make a ClojureScript version (using math.js and D3.js)
* break up the functions into an actual library