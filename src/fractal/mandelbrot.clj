(ns fractal.mandelbrot
  (:require [quil.core :as quil])
  (:import (javax.swing JFrame JLabel)
           (java.awt.image BufferedImage)
           (java.awt Dimension Color)
           (java.lang Number)
           (org.apache.commons.math.complex Complex)))

;; @nakkaya's algorithm goes like this:
;;
;;     1. Choose a rectangle in a complex plane.
;;     2. Map the image to the chosen complex plane.
;;     3. For each point/pixel, apply z = z\^2 + C
;;     4. Calculate how many iterations are required for the point.
;;     5. Paint the point according to it's iteration.
;;
;; Source: http://nakkaya.com/2009/09/29/fractals-in-clojure-mandelbrot-fractal/

(defn calc-iterations
  [p q max-iterations]
  (let [c (Complex. p q)]
    (loop [z c
           i 0]
      (cond
        (= 0 i)
        0

        (or (> (.abs z) 2.0)
            (> i max-iterations))
        (recur (.add c (.multiply z z)) (inc i))

        :else
        (- i 1)))))


(defn calc-iterations
  [p q max-iterations]
  (let [c (Complex. p q)]
    (loop [z c
           iterations 0]
      (if (or (> (.abs z) 2.0)
              (> iterations max-iterations))
        (if (= 0 iterations)
          0
          (- iterations 1))
        (recur (.add c (.multiply z z)) (inc iterations))))))


(defn calc-pixel-color
  [i max-iterations]
  (if (or (< i 10)
          (= i max-iterations))
    (Color. 0 0 0)
    (let [gray (int (/ (* i 255) max-iterations))
          r    gray
          g    (min (int (/ (* 5 (* gray gray)) 255)) 255)
          b    (min (int (+ 40 (/ (* 5 (* gray gray)) 255))) 255)]
      (Color. r g b))))

(defn generate
  [x y width height max-iterations
   graphics surface-width surface-height]
  (doseq [i (range surface-width)
          j (range surface-height)]
    (let [p          (+ x (* width  (/ i surface-width)))
          q          (+ y (* height (/ j surface-height)))

          iterations (calc-iterations p q max-iterations)
          color      (calc-pixel-color iterations max-iterations)]
      (.setColor graphics color)

      (.drawLine graphics i j i j))))


(defn draw
  "Example usage:
  (draw -2.1 -1.4 3.0 3.1 32 400 400)
  (draw -2.1 -1.4 3.0 3.1 100 400 400)"
  [x y width height iterations surface-width surface-height]
  (let [image (BufferedImage. surface-width surface-height BufferedImage/TYPE_INT_RGB)
        canvas (proxy [JLabel] []
                 (paint [g]
                   (.drawImage g image 0 0 this)))]
    (generate x y width height iterations
              (.createGraphics image) surface-width surface-height)
    (print @a)
    (doto (JFrame.)
      (.add canvas)
      (.setSize (Dimension. surface-width surface-height))
      (.show))))

(defn doit []
  (draw -2.1 -1.5 3.0 3.1 100 510 510))
