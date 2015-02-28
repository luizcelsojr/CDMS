/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/2/15
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */


import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants

class AlgebraTest extends GroovyTestCase {
    def db_path = '/Users/luizcelso/Dropbox/db/geoinfo-rest'
    //def db_path = '/Users/luizcelso/Dropbox/db/food'

    Operators basicOpr = new Operators()

    void setUp(){

    }

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


    List [] vPersonDataSmall = [
            [14, 'Andre', 'person'],
            [15, 'Ive', 'person'],
            [16, 'Celso', 'person'],
            [17, 'Bruno', 'person']
    ]

    List [] rConnDataSmall = [
            [3, 'I3', 'road'],
            [4, 'I4', 'road']
    ]

    List [] rConnData = [
            [1, 'null', 'I1', 'road'],
            [2, 'null', 'I2', 'road'],
            [3, 'null', 'I3', 'road'],
            [4, 'null', 'I4', 'road'],
            [5, 'null', 'I5', 'road'],
            [6, 'null', 'I6', 'road'],
            [7, 'null', 'I7', 'road'],
            [8, 'null', 'I8', 'road'],
            [9, 'null', 'I9', 'road'],
            [10, 'null', 'I10', 'road'],
            [11, 'Home-style cooking comfort food hand-made family friendly', 'Mamas', 'rest'],
            [12, 'Rotisserie and grill chicken dishes whole food family environment', 'Erreu', 'rest'],
            [13, 'Fastfood sandwiches soft drinks', 'FlashFast', 'rest']
    ]

    Table eConn = new Table(['id', 'id_n', 'label', 'Weight'],eConnData)

    Table eKnows = new Table(['id', 'id_n', 'label'],eKnowsData)

    Table rConnSmall = new Table(['id', 'Label', 'type'], rConnDataSmall)

    Table rConn = new Table(['id', "desc", 'Label', 'type'], rConnData)

    Table vPersonSmall= new Table(['id', 'Label', 'type'],vPersonDataSmall)

    Table eKnowsSmall = new Table(['id', 'id_n', 'label'],eKnowsDataSmall)

    Table vPerson= new Table(['id', 'Label', 'type'],vPersonData)


    void testAlgebra(){
        Map [] data = [[id:2, a:0, b:1], [id:1, a:5, b:2], [id:1, a:0, b:3]]
        Map [] data2 = [[id:2, idn:10, a:0, b:1], [id:1, idn:15, a:5, b:2], [id:1, idn:15, a:7, b:2], [id:1, idn:15, a:0, b:4], [id:1, idn:10, a:0, b:3]]

        Table t = new Table(data)

        //Test order by
        t.orderAsc(["id", "a", "b"])
        assertEquals(t.getRawContents(), [[id:1, a:0, b:3], [id:1, a:5, b:2], [id:2, a:0, b:1]])

        //test reduce
        t = new Table(data2)
        t.orderAsc(["id", "idn", "b"])
        t = basicOpr.reduce(t, ["id", "idn", "b"], [[aggr:"sum", func:"it.a", as:"sum"]])
        assertEquals(t.getRawContents(), [[id:1, idn:10, a:0, b:3, sum:0], [id:1, idn:15, a:5, b:2, sum:12], [id:1, idn:15, a:0, b:4, sum:0], [id:2, idn:10, a:0, b:1, sum:0]])

        //Test project
        t = basicOpr.project(t, ["id", "idn", "b"])
        assertEquals(t.getRawContents(), [[id:1, idn:10, b:3], [id:1, idn:15, b:2], [id:1, idn:15, b:4], [id:2, idn:10, b:1]])

        //Test step
        t = basicOpr.step(basicOpr.set(new Table(['id', 'Label', 'type'], vPersonDataSmall[1..2] ), ["it.id_n = it.id"]),
                          eKnowsSmall, Constants.BOTH)
        t.orderAsc(["id", "id_n"])
        assertEquals(t.getRowAt(1).id_n, 17)
        t = basicOpr.step(t, eKnowsSmall, Constants.BOTH)
        t.orderAsc(["id", "id_n"])
        assertEquals(t.getRowAt(1).id_n, 15)

        //Test beta (distance) and select

        rConnSmall = basicOpr.beta(rConnSmall, eConn, 4, {true}, Constants.BOTH, ['connects'], ["it.minDist = 0.0f", "it.maxDist = 0.0f"], ["it.minDist = it.minDist + it.Weight.toFloat()", "it.maxDist = it.maxDist + it.Weight.toFloat()"], ["id_n", "id"], [[aggr:"min", func:"it.minDist", as:"minDist"], [aggr:"max", func:"it.maxDist", as:"maxDist"]], [])
        rConnSmall.orderAsc(["id", "id_n"])
        rConnSmall = basicOpr.project(rConnSmall, ["id", "id_n", "minDist", "maxDist"])
        assertEquals(0.6, rConnSmall.getRowAt(6).minDist, 0.001) //rConnSmall = basicOpr.select(rConnSmall, {it.id == 3 && it.id_n == 7})
        assertEquals(1.4, rConnSmall.getRowAt(6).maxDist, 0.001)
        assertEquals(0.6, rConnSmall.getRowAt(18).minDist, 0.001) //rConnSmall = basicOpr.select(rConnSmall, {it.id == 4 && it.id_n == 6})
        assertEquals(1.2, rConnSmall.getRowAt(18).maxDist, 0.001)

        //Test beta (pagerank)

        t = basicOpr.beta(vPerson, eKnows, 3, {true}, Constants.BOTH, ['knows'], ["it.rank = 100.0f"], ["it.rank = it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])
        t.orderAsc('rank')
        assertEquals(70.49, t.getRowAt(0).rank, 0.1)
        assertEquals(98.03, t.getRowAt(3).rank, 0.1)

        //Test raname
        t = basicOpr.renameAll(vPerson, "new")
        assertEquals(15, t.getRowAt(1).new_id)

    }

    void testTable(){


        /*
        Table eKnows2 = basicOpr.set(eKnows, ["it.aux = it.id", "it.id = it.id_n", "it.id_n = it.aux"])
        eKnows2 = basicOpr.project(eKnows2, ["id", "id_n", "label"])
        eKnows2 = basicOpr.union(eKnows, eKnows2)
        eKnows2.orderAsc(["id", "id_n"])


        Table eCoKnows = basicOpr.step(vPerson, eKnows2, Constants.BOTH)
        eCoKnows = basicOpr.step(eCoKnows, eKnows2, Constants.BOTH)
        eCoKnows = basicOpr.project(eCoKnows, ["id", "Label", "type", "id_n", "label"])
        eCoKnows = basicOpr.select(eCoKnows, {it.id != it.id_n})
        eCoKnows = basicOpr.set(eCoKnows, ["it.label = 'fof'"])
        eCoKnows = basicOpr.unique(eCoKnows)

        Table vPerson2 = basicOpr.set(vPerson, ["it.idPerson = it.id", "it.labelPerson = it.Label"])
        vPerson2 = basicOpr.project(vPerson2, ["idPerson", "labelPerson"])
        vPerson2.print()

        eCoKnows.print()
        eCoKnows = basicOpr.tetaJoin(eCoKnows, vPerson2, {it.id_n == it.idPerson})
        eCoKnows.print()
        eCoKnows = basicOpr.rename(eCoKnows, [["idPerson", "id_n"]])
        eCoKnows.print()
        eCoKnows = basicOpr.project(eCoKnows, ["id", "Label", "label", "id_n", "labelPerson"])
        eCoKnows.print()

        Table t = basicOpr.beta(vPerson, eCoKnows, 3, {true}, Constants.BOTH, ['fof'], ["it.rank = 100.0f"], ["it.rank = it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])
        t.orderAsc('rank')
        t.print()  */

        vPerson.print()

        Table r = basicOpr.select(vPerson, {it.id == 16})

        Table t = basicOpr.beta(r, eKnows, 40, {true}, Constants.BOTH, ['knows'], ["it.rank = 1.0f"], ["it.rank = 0.8* it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], [])
        t.orderAsc('rank')

        t.print()




    }
}
