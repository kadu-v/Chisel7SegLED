# Chisel7SegLED


## How to generate Verilog code
```
$ sbt run Elaborate --target-dir out
```

## How to generate Vcd file of test
```
$ testOnly top.TopSepc -- -DwriteVcd=1
```