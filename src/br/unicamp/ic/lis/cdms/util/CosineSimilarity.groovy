package br.unicamp.ic.lis.cdms.util

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/24/15
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
class CosineSimilarity {
    static def similarity(l_seq, r_seq, degree=2) {
        def l_histo = countNgramFrequency(l_seq, degree)
        def r_histo = countNgramFrequency(r_seq, degree)

        dotProduct(l_histo, r_histo) /
                Math.sqrt(dotProduct(l_histo, l_histo) *
                        dotProduct(r_histo, r_histo))
    }

    static def countNgramFrequency(sequence, degree) {
        def histo = [:]
        def items = sequence.size()

        for (int i = 0; i + degree <= items; i++)
        {
            def gram = sequence[i..<(i + degree)]
            histo[gram] = 1 + histo.get(gram, 0)
        }
        histo
    }

    static def dotProduct(l_histo, r_histo) {
        def sum = 0
        l_histo.each { key, value ->
            sum = sum + l_histo[key] * r_histo.get(key, 0)
        }
        sum
    }

    static def stringSimilarity (l_str, r_str, degree=2) {
        similarity(l_str.toLowerCase().toCharArray(),
                r_str.toLowerCase().toCharArray(),
                degree)
    }
}
