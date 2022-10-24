
/**
 *
 * @author NAME
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.*;

public class TourneyMain implements ActionListener{

    public static void main(String[] args) throws Exception {
        new TourneyMain();
    }
    JFrame frame;
    ArrayList<Team> allTeams;
    ArrayList<Match> allMatches;
    JPanel matchesFrame, rankingFrame;
    JButton updateButton;
    JMenuItem addTeam;

    public TourneyMain() {
        frame = new JFrame();
        JMenuBar jmb=new JMenuBar();
        JMenu file=new JMenu("File");
        addTeam=new JMenuItem("Add Team");
        addTeam.addActionListener(new MenuThings());
        file.add(addTeam);
        jmb.add(file);
        frame.setJMenuBar(jmb);
        matchesFrame = new JPanel(new GridLayout(0, 3));
        frame.setLayout(new BorderLayout());

        allTeams = new ArrayList<Team>();
        try {
            Scanner scan = new Scanner(new File("teams.txt"));
            while (scan.hasNext()) {
                allTeams.add(new Team(scan.nextLine()));
            }
        } catch (Exception e) {
        }
        
        Collections.shuffle(allTeams);

        allMatches = new ArrayList<>();

        for (int i = 0; i < allTeams.size() - 1; i++) {
            for (int j = i + 1; j < allTeams.size(); j++) {
                allMatches.add(new Match(allTeams.get(i), allTeams.get(j)));
            }
        }
        
        Collections.shuffle(allMatches);

        buildMatchFrame();

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        JScrollPane scrollPane = new JScrollPane(matchesFrame);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new Dimension(200,400));
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(updateButton, BorderLayout.SOUTH);
        rankingFrame = new JPanel(new GridLayout(0, 2));
        buildRankingFrame();
        frame.add(rankingFrame, BorderLayout.EAST);

        frame.setPreferredSize(new Dimension(1500,500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void buildRankingFrame() {
        Collections.sort(allTeams);
        while (rankingFrame.getComponentCount() > 0) {
            rankingFrame.remove(0);
        }
        int count = 1;
        for (Team team : allTeams) {
            rankingFrame.add(new JLabel("" + count + "."));
            count++;
            rankingFrame.add(team.getLabel());
        }
    }
    
    public void buildMatchFrame(){
        Collections.sort(allMatches);
        
        while(matchesFrame.getComponentCount()>0)
            matchesFrame.removeAll();
        
        for (Match m : allMatches) {
            matchesFrame.add(m.getPanel());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buildMatchFrame();
        buildRankingFrame();
        frame.pack();
    }
    
    class MenuThings implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name=JOptionPane.showInputDialog("Please input a name").trim();
            Team newTeam=new Team(name);
            for (Team team : allTeams) {
                allMatches.add(new Match(newTeam, team));
            }
            allTeams.add(newTeam);
            buildMatchFrame();
            buildRankingFrame();
        }
        
    }

}
