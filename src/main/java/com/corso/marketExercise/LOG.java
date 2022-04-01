package com.corso.marketExercise;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class LOG {

        public static final Logger L = Logger.getLogger(LOG.class);
        public static LOG instance = null;


        {
            BasicConfigurator.configure();
        }


        private LOG() {

        }

        /**
         * @return instance
         */

        public static LOG getInstance() {
            if (instance == null)
                synchronized (LOG.class) {
                    instance = new LOG();
                }
            return instance;
        }

        public void info(String s) {
            L.info(s);
        }

        private void debug(String s) {
            L.debug(s);
        }

        public void warn(String s) {
            L.warn(s);
        }

        public void error(String s) {
            L.error(s);
        }

}
