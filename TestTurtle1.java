package turtle;

public class TestTurtle1 {
	public static void main(String[] args) {
        TurtleFrame f = new TurtleFrame(700, 500);
		Turtle t = new Turtle(200, 200, 0);
		f.add(t);
		t.fd(100);
		t.rt(90);
		t.fd(130);
		// �ϐ��錾
        int x = 300, y = 200, d = 100;
        
        // ��700�~����500�̃E�B���h�E����


        // �T�̐���
        // m ��x���W=�ϐ�x�̒l, y���W=�ϐ�y�̒l, �p�x = 180��
        // m1��x���W=�ϐ�x�̒l+�ϐ�d�̒l, y���W=�ϐ�y�̒l+�ϐ�d�̒l, �p�x = 0��
        Turtle m = new Turtle(x, y, 180);
        Turtle m1 = new Turtle(x + d, y + d, 0);
        
        // �F����N���X�̐��� (R:255, G:  0, B:  0�A�܂肾��������)
        java.awt.Color c = new java.awt.Color(255, 0, 0);
        
        // �Tm1�̐F��ݒ�
        m1.setColor(c); 
        
        // �t���[���ɋT��ǉ�
        //f.add(m);
        //f.add(m1);
        
        // �T�̑O�i
        //m.fd(d);
        //m1.fd(d);
        
        // �T�̉�]
        //m.lt(90);
        //m1.lt(90);
        
        // �ϐ�d�̒l�ύX(����)
        d = d / 2;     
        
        // �T�̑O�i
        //m.fd(d);
        //m1.fd(d);
        
        // �Tm1��m�܂ňړ�
        //m1.moveTo(m);
	}
}
