(ns fractal.core
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
  (setup  [] "Lays the groundwork for the generated fractal. Is only called once.")
  (draw   [] "Called immediately after setup has finished, and then repeatedly until stopped.")
  (sketch [] "FIXME"))


(defn draw [& args] 
  (m/draw -2.1 -1.5 3.0 3.1 100 510 510))
