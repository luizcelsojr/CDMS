package br.unicamp.ic.lis.cdms.benchmark

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/25/12
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */

static float closureBenchmark(closure){
    def start, now
    start = System.currentTimeMillis()
    closure.call()
    now = System.currentTimeMillis()
    now - start
}