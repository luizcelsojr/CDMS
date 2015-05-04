package gui

import br.unicamp.ic.lis.cdms.RunQuery
import groovy.model.DefaultTableColumn
import groovy.model.DefaultTableModel
import groovy.model.PropertyModel
import groovy.swing.SwingBuilder

import javax.swing.JFileChooser
import javax.swing.JTable
import javax.swing.event.TableModelListener
import javax.swing.table.TableModel
import java.awt.BorderLayout       as BL
import javax.swing.WindowConstants as WC
import javax.swing.BorderFactory   as BF

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/7/15
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */

swing = new SwingBuilder()

frame = swing.frame(title:'CDMS Console',
        location:[50,50], size:[1300,600],
        defaultCloseOperation:WC.EXIT_ON_CLOSE, show: true) {

    panel (border:BF.createEmptyBorder(6,6,6,6)) {
        borderLayout()
        vbox (constraints: BL.NORTH){
            hbox {
                hstrut(width:10)
                label 'Language: '
                comboLanguage = comboBox(items:['beta', 'cypher'], constraints:BL.NORTH, toolTipText:'Choose the language')
                dir = textField(id:'dir', editable: false, '/Users/luizcelso/Dropbox/db/geoinfo')
                button(text:'Choose Database Dir',
                        actionPerformed: {dir.text = fileChooser()})
            }
        }
        vbox(constraints: BL.CENTER){
            hbox(border:BF.createTitledBorder('Query')) {
                scrollPane{
                    def defaultText = ""
                    def file = new File("/Users/luizcelso/Dropbox/workspace/CDMS2/queries/query-beta-algebra.beta")
                    if (file.exists()) defaultText = file.getText()
                    text = textPane(text:defaultText)
                }
            }
            hbox(border:BF.createTitledBorder('Output')) {
                scrollPane {

                    tableOut = table() {
                        tableModel() {
                        }
                    }
                }
            }

        }

        hbox (constraints: BL.SOUTH){
            button(text:'Fire this badboy...',
                    actionPerformed: {fireQuery(dir.text, comboLanguage.selectedItem, text.text, tableOut)})
        }

    }

}

String fileChooser(){
    def initialPath = System.getProperty("user.dir");
    def path = ''
    JFileChooser fc = new JFileChooser(initialPath);
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    int result = fc.showOpenDialog( null );
    switch ( result )
    {
        case JFileChooser.APPROVE_OPTION:
            File file = fc.getSelectedFile();

            path =  file.toString(); // fc.getCurrentDirectory().getAbsolutePath()

            break;
        case JFileChooser.CANCEL_OPTION:
        case JFileChooser.ERROR_OPTION:
            break;
    }
    return path
}

boolean checkDBPath(String db_path){
    File file = new File(db_path + '/neostore.nodestore.db')

    println file.toString()

    return file.exists()
}


def fireQuery(db_path, language, query,  JTable tableOut){
    swing = new SwingBuilder()

    if (!checkDBPath(db_path)) {
        def pane = swing.optionPane(message:"Dude, " + db_path + " doesn't seem to be a valid Neo4J directory. \nMake sure you get the proper dir. And make sure you are always a nice, thoughtful, and happy person!!!")
        def dialog = pane.createDialog(frame, 'Error!!! Ahhhhh.. Oh nooooooo!!!')
        dialog.show()
        return
    }

    println query
    def rq = new RunQuery()
    results = rq.run(db_path, language, query)

    //results = [[name:'celso'], [name:'luiz'], [name:'jade', favColor:'pink']]
    def headers = [] as Set
    headers.add('rowCount')
    results.each {headers.addAll(it.keySet())}


    tableOut.model = swing.tableModel()

    headers.each{
        tableOut.model.addColumn(new DefaultTableColumn(it, new PropertyModel(tableOut.model.rowModel, it)))
    }

    def count = 0
    results.each{
        row = it
        row["rowCount"] = count++
        tableOut.model.rowsModel.value.add(row)
    }
    tableOut.model.fireTableStructureChanged()

    tableOut.model.fireTableDataChanged()

}
