(ns dwcode.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [dwcode.core-test]))

(doo-tests 'dwcode.core-test)

