package turtle;

public class TestTurtle1 {
	public static void main(String[] args) {
        TurtleFrame f = new TurtleFrame(700, 500);
		Turtle t = new Turtle(200, 200, 0);
		f.add(t);
		t.fd(100);
		t.rt(90);
		t.fd(130);
		// 変数宣言
        int x = 300, y = 200, d = 100;
        
        // 幅700×高さ500のウィンドウ生成


        // 亀の生成
        // m のx座標=変数xの値, y座標=変数yの値, 角度 = 180°
        // m1のx座標=変数xの値+変数dの値, y座標=変数yの値+変数dの値, 角度 = 0°
        Turtle m = new Turtle(x, y, 180);
        Turtle m1 = new Turtle(x + d, y + d, 0);
        
        // 色制御クラスの生成 (R:255, G:  0, B:  0、つまりだいたい赤)
        java.awt.Color c = new java.awt.Color(255, 0, 0);
        
        // 亀m1の色を設定
        m1.setColor(c); 
        
        // フレームに亀を追加
        //f.add(m);
        //f.add(m1);
        
        // 亀の前進
        //m.fd(d);
        //m1.fd(d);
        
        // 亀の回転
        //m.lt(90);
        //m1.lt(90);
        
        // 変数dの値変更(半分)
        d = d / 2;     
        
        // 亀の前進
        //m.fd(d);
        //m1.fd(d);
        
        // 亀m1をmまで移動
        //m1.moveTo(m);
	}
}
