//groovybeta

Table t


t = opr.scanFilterV({it.id==3})
println '=>t'
t.print()
println 'xxxxxxx'

t = opr.beta(t, 2, {true}, Constants.OUTBOUND, ['connects'], ["it.dist = 0.0f"], ["it.dist = it.dist + e.Weight.toFloat()"], [[aggr:"min", func:"it.dist", as:"minDist"], [aggr:"max", func:"it.dist", as:"maxDist"]], [])
t.orderAsc('minDist')
t.print()
println 'xxxxxxx'

return t
