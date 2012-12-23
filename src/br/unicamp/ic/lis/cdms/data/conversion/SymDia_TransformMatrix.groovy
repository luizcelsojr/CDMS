package br.unicamp.ic.lis.cdms.data.conversion

def header = null

def dir = "/lishome-ext/luizcelso/datasets/enfermagem/"
def files = ["grupo1a", "grupo1b", "grupo1c", "grupo2"]


def outf = new File(dir + "all.out.csv")

def edges = [:]

files.each{ file ->
	header = null
	new File(dir + file + ".csv").splitEachLine(",") {fields ->
		if (!header) {
			header = fields
		} else {
			(1..(header.size()-1)).each{
				//if (fields[it] != '0') outf << fields[0] + " " + header[it] + " " + fields[it] + "\n"
					edge = fields[0] + " " + header[it]
					if (!edges.containsKey(edge)){
						edges[edge] = fields[it].toFloat()
						print edge + " " + fields[it] + "\n"
					}
					else {
						println "duplicate: $edge " + edges[edge] + " X $file" + fields[it]
						edges[edge] = (edges[edge] + fields[it].toFloat())/2.0
					}
				
			}
		}
	  }

}

outf << "Source Target Weight\n"

edges.each{key, value->
	 if (value != 0.0) outf << key + " " + value + "\n"
}

