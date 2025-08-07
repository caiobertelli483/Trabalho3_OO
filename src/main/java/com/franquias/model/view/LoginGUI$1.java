package com.franquias.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class LoginGUI$1 extends KeyAdapter {
   LoginGUI$1(LoginGUI var1) {
      this.this$0 = var1;
   }

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == 10) {
         this.this$0.realizarLogin();
      }

   }
}
