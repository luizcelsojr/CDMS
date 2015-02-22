/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/2/15
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */


import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.source.Neo4jDB
import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants

class AlgebraTest extends GroovyTestCase {
    def db_path = '/Users/luizcelso/Dropbox/db/geoinfo-rest'
    //def db_path = '/Users/luizcelso/Dropbox/db/food'

    Operators basicOpr = new Operators()

    void setUp(){

    }


    void testAlgebra(){
        Map [] data = [[id:2, a:0, b:1], [id:1, a:5, b:2], [id:1, a:0, b:3]]
        Map [] data2 = [[id:2, idn:10, a:0, b:1], [id:1, idn:15, a:5, b:2], [id:1, idn:15, a:7, b:2], [id:1, idn:15, a:0, b:4], [id:1, idn:10, a:0, b:3]]

        Table t = new Table(data)




        //Test order by

        t.orderAsc(["id", "a", "b"])
        //t.print()
        //println 'xxxxxxx'

        assertEquals(t.getRawContents(), [[id:1, a:0, b:3], [id:1, a:5, b:2], [id:2, a:0, b:1]])

        //test reduce

        t = new Table(data2)

        t.orderAsc(["id", "idn", "b"])
        //t.print()
        //println 'xxxxxxx'

        t = basicOpr.reduce(t, ["id", "idn", "b"], [[aggr:"sum", func:"it.a", as:"sum"]])
        assertEquals(t.getRawContents(), [[id:1, idn:10, a:0, b:3, sum:0], [id:1, idn:15, a:5, b:2, sum:12], [id:1, idn:15, a:0, b:4, sum:0], [id:2, idn:10, a:0, b:1, sum:0]])

        //t.print()
        //println 'xxxxxxx'

        t = basicOpr.project(t, ["id", "idn", "b"])
        assertEquals(t.getRawContents(), [[id:1, idn:10, b:3], [id:1, idn:15, b:2], [id:1, idn:15, b:4], [id:2, idn:10, b:1]])

        List [] eConnData = [
                [1, 3, 'connects', '0.2'],
                [3, 1, 'connects', '0.2'],
                [3, 6, 'connects', '0.3'],
                [4, 3, 'connects', '0.3'],
                [6, 3, 'connects', '0.3'],
                [6, 5, 'connects', '0.2'],
                [6, 7, 'connects', '0.3'],
                [7, 6, 'connects', '0.3'],
                [7, 9, 'connects', '0.4'],
                [8, 2, 'connects', '0.5'],
                [8, 6, 'connects', '0.3'],
                [8, 11, 'connects', '0.4'],
                [8, 12, 'connects', '0.1'],
                [9, 8, 'connects', '0.4'],
                [9, 13, 'connects', '0.2'],
                [10, 8, 'connects', '0.2'],
                [11, 2, 'connects', '0.1'],
                [12, 6, 'connects', '0.2'],
                [13, 8, 'connects', '0.2']]

        List [] eKnowsData = [
                [14, 17, 'knows'],
                [14, 19, 'knows'],
                [14, 21, 'knows'],
                [15, 16, 'knows'],
                [15, 17, 'knows'],
                [15, 20, 'knows'],
                [15, 21, 'knows'],
                [16, 22, 'knows'],
                [17, 18, 'knows'],
                [18, 20, 'knows'],
                [18, 22, 'knows'],
                [19, 20, 'knows'],
                [19, 21, 'knows']
        ]

        List [] eKnowsDataSmall = [
                [14, 17, 'knows'],
                [15, 16, 'knows'],
                [15, 17, 'knows']
        ]

        List [] vPersonDataSmall = [
                [14, 'Andre', 'person'],
                [15, 'Ive', 'person'],
                [16, 'Celso', 'person'],
                [17, 'Bruno', 'person']
        ]

        List [] rConnData = [
                [3, 'I3', 'road']
        ]


        Table eConn = new Table(['id', 'id_n', 'label', 'Weight'],eConnData)

        Table eKnows = new Table(['id', 'id_n', 'label'],eKnowsData)

        Table rConn = new Table(['id', 'Label', 'type'], rConnData)

        Table vPersonSmall= new Table(['id', 'Label', 'type'],vPersonDataSmall)

        Table eKnowsSmall = new Table(['id', 'id_n', 'label'],eKnowsDataSmall)



        //eConn.print()
        //rConn.print()
        /*
        //Test step
        t = basicOpr.step(basicOpr.set(new Table(['id', 'Label', 'type'], vPersonDataSmall[1..2] ), ["it.id_n = it.id"]),
                          eKnowsSmall, Constants.BOTH)
        t.orderAsc(["id", "id_n"])
        assertEquals(t.getRowAt(1).id_n, 17)
        t = basicOpr.step(t, eKnowsSmall, Constants.BOTH)
        t.orderAsc(["id", "id_n"])
        assertEquals(t.getRowAt(1).id_n, 15)


        //Test beta (distance) and select

        rConn = basicOpr.beta(rConn, eConn, 4, {true}, Constants.BOTH, ['connects'], ["it.dist = 0.0f"], ["it.dist = it.dist + it.Weight.toFloat()"], ["id", "id_n"], [[aggr:"min", func:"it.dist", as:"minDist"], [aggr:"max", func:"it.dist", as:"maxDist"]], [])
        rConn = basicOpr.select(rConn, {it.id_n == 7})

        assertEquals(0.6, rConn.getRowAt(0).minDist, 0.001)
        assertEquals(1.4, rConn.getRowAt(0).maxDist, 0.001)


       vPersonSmall.print()
       eKnowsSmall.print()
       t = basicOpr.step(vPersonSmall, eKnowsSmall, Constants.BOTH)
       t.orderAsc(["id", "id_n"])
       t.print()
        */

        t = basicOpr.beta(vPersonSmall, eKnowsSmall, 2, {true}, Constants.BOTH, ['knows'], ["it.rank = 100.0f"], ["it.rank = it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])
        t.print()



    }

    void testTable(){
        List [] eConnData = [
                [1, 3, 'connects', '0.2'],
                [3, 1, 'connects', '0.2'],
                [3, 6, 'connects', '0.3'],
                [4, 3, 'connects', '0.3'],
                [6, 3, 'connects', '0.3'],
                [6, 5, 'connects', '0.2'],
                [6, 7, 'connects', '0.3'],
                [7, 6, 'connects', '0.3'],
                [7, 9, 'connects', '0.4'],
                [8, 2, 'connects', '0.5'],
                [8, 6, 'connects', '0.3'],
                [8, 11, 'connects', '0.4'],
                [8, 12, 'connects', '0.1'],
                [9, 8, 'connects', '0.4'],
                [9, 13, 'connects', '0.2'],
                [10, 8, 'connects', '0.2'],
                [11, 2, 'connects', '0.1'],
                [12, 6, 'connects', '0.2'],
                [13, 8, 'connects', '0.2']]

        List [] eKnowsData = [
                [14, 17, 'knows'],
                [14, 19, 'knows'],
                [14, 21, 'knows'],
                [15, 16, 'knows'],
                [15, 17, 'knows'],
                [15, 20, 'knows'],
                [15, 21, 'knows'],
                [16, 22, 'knows'],
                [17, 18, 'knows'],
                [18, 20, 'knows'],
                [18, 22, 'knows'],
                [19, 20, 'knows'],
                [19, 21, 'knows']
        ]

        List [] vPersonData = [
            [14, 'Andre', 'person'],
            [15, 'Ive', 'person'],
            [16, 'Celso', 'person'],
            [17, 'Bruno', 'person'],
            [18, 'Daniel', 'person'],
            [19, 'Lucas', 'person'],
            [20, 'Jacque', 'person'],
            [21, 'Matheus', 'person'],
            [22, 'Jordi', 'person']
          ]

        List [] rConnData = [
                [3, 'I3', 'road']
            ]


        Table eConn = new Table(['id', 'id_n', 'label', 'Weight'],eConnData)

        Table vPerson= new Table(['id', 'Label', 'type'],vPersonData)



        Table rConn = new Table(['id', 'Label', 'type'], rConnData)

        Table eKnows = new Table(['id', 'id_n', 'label'],eKnowsData)

        Table eKnows2 = basicOpr.set(eKnows, ["it.aux = it.id", "it.id = it.id_n", "it.id_n = it.aux"])
        eKnows2 = basicOpr.project(eKnows2, ["id", "id_n", "label"])
        eKnows = basicOpr.union(eKnows, eKnows2)
        eKnows.orderAsc(["id", "id_n"])

        Table celsoKnows = new Table(['id'], [[16]])
        Table ePersonKnows = basicOpr.set(vPerson, ["it.id_n = it.id"])

        //eKnows.print()
        /*
        Table eCoKnows = basicOpr.step(ePersonKnows, eKnows, Constants.BOTH)
        eCoKnows = basicOpr.step(eCoKnows, eKnows, Constants.BOTH)
        eCoKnows = basicOpr.select(eCoKnows, {it.id != it.id_n})
        eCoKnows = basicOpr.set(eCoKnows, ["it.label = 'fof'"])
        eCoKnows = basicOpr.unique(eCoKnows)
        //eCoKnows = basicOpr.tetaJoin(eCoKnows, basicOpr.set(vPerson, ["it.idPerson = it.id", "it.labelPerson = it.Label"]), {it.id_n == it.idPerson})
        //eCoKnows.print()
        */
        //vPerson.print()
        //eKnows.print()

        //Table eCoKnows = basicOpr.beta(vPerson, eKnows, 3, {true}, Constants.BOTH, ['knows'], ["it.rank = 100.0f"], ["it.rank = it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])


        //eCoKnows.print()

    }
}
