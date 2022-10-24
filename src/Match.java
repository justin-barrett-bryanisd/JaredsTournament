
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author jbarrett
 */
public class Match implements ActionListener,Comparable {

    Team team1, team2;
    int matchNum;
    static int matchCount=0;
    boolean played;
    JPanel matchPanel;
    JCheckBox assigned;

    JCheckBox cb1, cb2;

    public Match(Team t1, Team t2) {
        matchCount++;
        matchNum=matchCount;
        team1 = t1;
        team2 = t2;
        played=false;
        matchPanel=createPanel();
    }
    
    public JPanel getPanel(){
        return matchPanel;
    }

    public JPanel createPanel() {
        JPanel temp2=new JPanel(new BorderLayout());
        JPanel temp = new JPanel(new GridLayout(0, 1));
        cb1 = team1.getBox();
        cb1.addActionListener(this);
        cb1.setActionCommand(team1.name+" beat "+team2.name+"?");
        temp.add(cb1);
        cb2 = team2.getBox();
        cb2.addActionListener(this);
        temp.add(cb2);
        cb2.setActionCommand(team2.name+" beat "+team1.name+"?");
        temp2.add(temp,BorderLayout.CENTER);
        DecimalFormat df=new DecimalFormat("0000");
        temp2.add(new JLabel(df.format(matchNum)),BorderLayout.NORTH);
        
        assigned=new JCheckBox("Game assigned? ");
        temp2.add(assigned, BorderLayout.SOUTH);
        
        temp2.setBorder(BorderFactory.createLineBorder(Color.black));
        return temp2;
    }
    
    public int getTotalGames(){
        return team1.games+team2.games;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int choice=JOptionPane.showConfirmDialog(null, e.getActionCommand());
        if(choice==0){
            if(cb1.getActionCommand().equals(e.getActionCommand())){
                team1.win(team2);
                team2.loss(team1);
                cb1.setEnabled(false);
                cb2.setEnabled(false);
            }
            else{
                team2.win(team1);
                team1.loss(team2);
                cb1.setEnabled(false);
                cb2.setEnabled(false);
            }
            played=true;
        }
        else{
            cb1.setSelected(false);
            cb2.setSelected(false);
        }
    }

    @Override
    public int compareTo(Object o) {
        Match other=(Match) o;
        if(!played && assigned.isSelected())
            return -1;
        if(!other.played && other.assigned.isSelected())
            return 1;
        if(played && other.played)
            return getTotalGames()-other.getTotalGames();
        if(!played && !other.played)
            return getTotalGames()-other.getTotalGames();
        if(played)
            return 1;
        return -1;
    }
}
