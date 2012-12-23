package br.unicamp.ic.lis.girdb.data.conversion

def header = null

def dir = "/lishome-ext/luizcelso/datasets/enfermagem/"
def file = dir + "Rodrigo-symtom-diagnose Edges.csv"


def outf = new File(dir + "no-diag.out.csv")

def edges = [:]
def map = [:]


outf << "Source Target Weight\n"
//println "Source Target\n"

new File(file).splitEachLine(",") {fields ->
	if (!header) {
		header = fields
	} else {
		source = fields[0]
		target = fields[1]

		if (!map.containsKey(target)){
			map[target] = [source]
		}
		else {
			map[target].each{
				edge = [source,it].sort()
				if (edges.containsKey(edge)){
					edges[edge]++
				}else{
					edges[edge] = 1
				}
			}
			map[target].add(source)
			
		}


	}
}


edges.each{key, value ->
	outf << key[0] + " " + key[1] + " " + value + "\n"
	
}


