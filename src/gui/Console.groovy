package gui

import br.unicamp.ic.lis.cdms.RunQuery
import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.ui.text.TextEditor

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/7/15
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
class UIConsole {
    def count = 0
    def run(){

        new SwingBuilder().edt {
            frame(title:'Frame', size:[1000,800], show: true) {
                borderLayout()
                textlabel = label(text:"Would you write me a nice query today?", constraints: BL.NORTH)
                comboLanguage = comboBox(items:['cypher', 'beta'], constraints:BL.NORTH, toolTipText:'Choose the language')
                text = textPane(text:"test", constraints: BL.CENTER)
                button(text:'Fire this badboy...',
                        actionPerformed: {fireQuery("/Users/luizcelso/Dropbox/db/geoinfo", comboLanguage.selectedItem, text.text)},
                        constraints:BL.SOUTH)
            }
        }
    }

    def fireQuery(db_path, language, query){
        println query
        println language
//        System.exit(0)
        def rq = new RunQuery()
        rq.run(db_path, language, query)
    }
}

c = new UIConsole()
c.run()
