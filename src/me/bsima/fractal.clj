(ns me.bsima.fractal
  (:require [clj-time.core  :as t]
            [clj-time.local :as l]
            [quil.core      :as q])

  ;; TODO: remove java deps
  (:import (javax.swing JFrame JLabel)
           (java.awt.image BufferedImage)
           (java.awt Dimension Color)
           (java.lang Number)
           (org.apache.commons.math.complex Complex)))




(defprotocol IFractal
  "A specification for drawing fractals."
  (setup  [_] "Lays the groundwork for the generated fractal. Is only called once.")
  (draw   [_] "Called immediately after setup has finished, and then repeatedly until stopped.")
  (sketch [_] "FIXME"))


