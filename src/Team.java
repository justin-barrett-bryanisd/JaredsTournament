
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 *
 * @author jbarrett
 */
public class Team implements Comparable{

    String name;
    ArrayList<Team> playedTeams;
    Color color;
    int games, wins;
    Font f=new Font("Lucida Sans", Font.PLAIN, 32);
    
    public Team(String n){
        name=n;
        playedTeams=new ArrayList<>();
        Random r=new Random();
        color=new Color(150+r.nextInt(100),150+r.nextInt(100),150+r.nextInt(100));        
    }
    
    public double getWinPercentage(){
        if(games==0) return 0;
        return 1.0 * wins / games;
    }
    
    public double getOpponentsAverage(){
        if(games==0) return 0;
        double sum=0;
        
        for (Team playedTeam : playedTeams) {
            sum+=playedTeam.getWinPercentage();
        }
        return sum/playedTeams.size();
    }
    
    public void win(Team other){
        games++; wins++;
        playedTeams.add(other);
    }
    public void loss(Team other){
        games++;
        playedTeams.add(other);
    }

    @Override
    public int compareTo(Object o) {
        Team other=(Team) o;
        if(other.getWinPercentage()>getWinPercentage())
            return 1;
        if(other.getWinPercentage()<getWinPercentage())
            return -1;
       if(other.getOpponentsAverage()>getOpponentsAverage())
            return 1;
        if(other.getOpponentsAverage()<getOpponentsAverage())
            return -1; 
        return 0;

    }
    
    public JCheckBox getBox(){
        JCheckBox temp=new JCheckBox(name);
        temp.setOpaque(true);
        temp.setBackground(color);
        temp.setFont(f);
        return temp;
    }
    
    public JLabel getLabel(){
        DecimalFormat df=new DecimalFormat("0.00");
        String str=name+"("+wins+"-"+(games-wins)+")  "+df.format(getOpponentsAverage());
        JLabel temp=new JLabel(str);
        temp.setOpaque(true);
        temp.setBackground(color);
        
        return temp;
    }
    

}
