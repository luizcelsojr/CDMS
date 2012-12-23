package br.unicamp.ic.lis.cdms;
import groovy.lang.Binding;
import groovy.lang.Script;

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/19/12
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */


public class RQJava {
    public static void main(String[] args) {
        // lets pass in some variables
        Binding binding = new Binding();
        binding.setVariable("args", args);

        Script rq = new RQ(binding);
        rq.run();
    }
}