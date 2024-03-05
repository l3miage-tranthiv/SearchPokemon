package com.example.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;



public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean continueInput = true;

            while (continueInput) {
                try {
                    // Demander à l'utilisateur de saisir le numéro du Pokémon
                    String pokemonNumber = JOptionPane.showInputDialog("Entrez le numéro du Pokémon :");
                    if (pokemonNumber == null || pokemonNumber.isEmpty()) {
                        continue;
                    }

                    // Construire l'URL de l'image du Pokémon
                    String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonNumber + ".png";

                    // Télécharger l'image
                    Image image = ImageIO.read(new URL(imageUrl));

                    // Créer le composant d'affichage de l'image
                    ImageIcon imageIcon = new ImageIcon(image);
                    //changer la taille image
                    Image img = imageIcon.getImage();
                    Image newimg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(newimg);

                    JLabel label = new JLabel(imageIcon);

                    // export les information de pokemon numbers
                    URL url = new URL("https://pokeapi-proxy.freecodecamp.rocks/api/pokemon/" + pokemonNumber);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    JSONObject obj = new JSONObject(content.toString());
                    
                    // Extraction de la valeur de base_experience
                    int baseExperience = obj.getInt("base_experience");
                    int height = obj.getInt("height");
                    int weight = obj.getInt("weight");
                    String name = obj.getString("name");
                    JSONObject sprites = obj.getJSONObject("sprites");
                    
            
                    
                    // Créer le JFrame pour afficher l'image
                    JFrame frame = new JFrame("Pokémon");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new FlowLayout());
                    frame.setSize(700, 700);
                    //frame.add(label);
                    //Add panel
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                    frame.add(panel);
                    // Create an Iterator for the keys
                    Iterator<String> keys = sprites.keys();
                    while(keys.hasNext()) {
                        // Get the key
                        String key = keys.next();
                        // Get the value
                        String value = sprites.getString(key);
                        // Do something...
                        if(value != null && !value.isEmpty()) {
                            Image image2 = ImageIO.read(new URL(value));
                            ImageIcon imageIcon2 = new ImageIcon(image2);
                            JLabel label2 = new JLabel(imageIcon2);
                            panel.add(label2);
                        }
                    }
                    
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

                    // Ajouter les informations avec des JLabels
                    // Create the JLabel
                    JLabel nameLabel = new JLabel(name.toUpperCase());

                    // Set the text color to red
                    nameLabel.setForeground(Color.BLUE);

                    // Add the JLabel to the frame
                    frame.add(nameLabel);

                    frame.add(Box.createRigidArea(new Dimension(10, 10))); // Espace entre les labels
                    frame.add(new JLabel("Base Experience: " + baseExperience));
                    frame.add(Box.createRigidArea(new Dimension(10, 10))); // Espace entre les labels
                    frame.add(new JLabel("Height: " + height));
                    frame.add(Box.createRigidArea(new Dimension(10, 10))); // Espace entre les labels
                    frame.add(new JLabel("Weight: " + weight));
                    frame.add(Box.createRigidArea(new Dimension(10, 10))); // Espace entre les labels
                    
                    frame.setVisible(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'image.");
                }

                // Demander à l'utilisateur s'il souhaite continuer
                int option = JOptionPane.showConfirmDialog(null, "Voulez-vous entrer un autre numéro de Pokémon ?", "Continuer ?", JOptionPane.YES_NO_OPTION);
                if (option != JOptionPane.YES_OPTION) {
                    continueInput = false;
                }
            }
        });
    }
}
