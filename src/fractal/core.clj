(ns fractal.core
  (:refer-clojure :exclude [+ * < min])
  (:require [clj-time.core :as t]
            [clj-time.local :as l])
  (:gen-class)
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
  "docstring here"
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
  "doc"
  [iterations max-iterations]
  (if (or (< iterations 10)
          (= iterations max-iterations))
    (Color. 0 0 0)
    (let [gray (int (/ (* iterations 255) max-iterations))
          r    gray
          g    (min (int (/ (* 5 (* gray gray)) 255)) 255)
          b    (min (int (+ 40 (/ (* 5 (* gray gray)) 255))) 255)]
      (Color. r g b))))

(defn generate
  "docs"
  [x y width height max-iterations graphics surface-width surface-height]
  (doseq [i (range surface-width)
          j (range surface-height)]
    (let [p (+ x (* width  (/ i surface-width)))
          q (+ y (* height (/ j surface-height)))
          iterations (calc-iterations p q max-iterations)
          color (calc-pixel-color iterations max-iterations)]
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
    (doto (JFrame.)
      (.add canvas)
      (.setSize (Dimension. surface-width surface-height))
      (.show))))


;; The draw function takes the following arguments:
;;
;; [x y width height iterations surface-width surface-height]
;;
;; ...which you could describe like this:
;;
;; [hour minute day month iterations surface-width surface-height]
;;
;; ...at least in theory. In practice, it doesn't work so well.
;; I need to do a bunch of fancy arithmetic to figure out how to
;; fit it all together nicely...
(comment
(defn time-fractal
  "Creates a fractal image based on the current time"
  [iterations]
  (let [local         #(l/local-now)
        hour           (t/hour (local))
        minute         (t/minute (local))
        day            (t/day (local))
        month          (t/month (local))
        max-val        (max hour minute day month)
        surface-width  max-val
        surface-height max-val]
    (println hour minute day month iterations surface-width surface-height)))
)


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (draw -2.1 -1.5 3.0 3.1 100 510 510))
