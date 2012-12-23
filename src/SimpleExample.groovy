import com.tinkerpop.gremlin.groovy.Gremlin

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory

class SimpleExampleX { 
  static {
    Gremlin.load()
  }
  public List exampleMethod() {
    Graph g = TinkerGraphFactory.createTinkerGraph()
    def results = []
    g.v(1).out('knows').fill(results)
	println(results)
    return results
  }
}

s = new SimpleExampleX()


s.exampleMethod()