(ns fractal.core
  (:require [clj-time.core :as t]
            [clj-time.local :as l]
            [fractal.mandelbrot :as m])
  (:gen-class)
  (:import (javax.swing JFrame JLabel)
           (java.awt.image BufferedImage)
           (java.awt Dimension Color)
           (java.lang Number)
           (org.apache.commons.math.complex Complex)))


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
    (m/draw hour minute day month iterations surface-width surface-height)))
)


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (draw -2.1 -1.5 3.0 3.1 100 510 510))
