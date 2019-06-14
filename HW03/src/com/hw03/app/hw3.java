package com.hw03.app;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class hw3 {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";
    private Connection con;

    private JPanel genresPanel;
    private JPanel countryPanel;
    private JPanel filmingCountryPanel;
    private JPanel searchOptionPanel;
    private JPanel ratingPanel;
    private JPanel reviewsPanel;
    private JPanel yearPanel;
    private JPanel tagPanel;
    private JPanel tagWeightPanel;
    private DefaultListModel resultListModel;
    private JScrollPane resultListScroller;
    private JLabel resultLable;
    private JPanel queryPanel;
    private JLabel detailsLable;
    private JScrollPane detailsScroller;
    private JPanel resultPanel;
    private JPanel detailsPanel;


    private ArrayList<String> selectedGenres;
    private ArrayList<String> selectedCountry;
    private ArrayList<String> selectedFilmingLocation;
    private String searchOption;
    private String ratingRelation;
    private String ratingValue;
    private String numReviewsRelation;
    private String numReviewsValue;
    private String yearFrom;
    private String yearTo;
    private ArrayList<String> selectedTags;
    private String tagWeightRelation;
    private String tagWeightValue;


    public hw3() {

        genresPanel = createGenresPanel();
        countryPanel = createCountryPanel();
        filmingCountryPanel = createFilmingCountryPanel();
        searchOptionPanel = createSearchOptionPanel();
        ratingPanel = createRatingPanel();
        reviewsPanel = createReviewsPanel();
        yearPanel = createYearPanel();
        tagPanel = createTagPanel();
        tagWeightPanel = createTagWeightPanel();
        resultListModel = new DefaultListModel();
        resultListScroller = new JScrollPane();
        resultLable = new JLabel();
        resultPanel = createResultPanel();
        queryPanel = createQueryPanel();
        detailsLable = new JLabel();
        detailsScroller = new JScrollPane();
        detailsPanel = createDetailsPanel();


        selectedGenres = new ArrayList<>();
        selectedCountry = new ArrayList<>();
        selectedFilmingLocation = new ArrayList<>();
        selectedTags = new ArrayList<>();
        searchOption = "AND";
        ratingRelation = "=";
        numReviewsRelation = "=";
        tagWeightRelation = "=";
        ratingValue = "";
        numReviewsValue = "";
        yearFrom = "";
        yearTo = "";
        tagWeightValue = "";

    }

    public static void main(String[] args) {

        hw3 app = new hw3();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app.createAndShowGUI();
            }
        });

    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("App");
        frame.setPreferredSize(new Dimension(1200,800));
//        frame.setBounds(100,100,400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel title = new JPanel();
        title.add(new JLabel("MOVIE"));
        title.setBackground(new Color(55, 129,182));
        main.add(title);

        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();

        main.add(topPanel);
        main.add(bottomPanel);

        frame.add(main);

        frame.pack();
        frame.setVisible(true);
    }


    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(createTopLeftPanel());
        topPanel.add(createTopRightPanel());

        return topPanel;
    }



    private JPanel createTopLeftPanel() {
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.Y_AXIS));
        topLeftPanel.add(createTopLeftDisplayPanel());
        topLeftPanel.add(Box.createVerticalStrut(20));
        topLeftPanel.add(searchOptionPanel);
        topLeftPanel.add(Box.createVerticalStrut(30));

        return topLeftPanel;
    }



    private JPanel createTopLeftDisplayPanel() {
        JPanel topLeftDisplayPanel = new JPanel();
        topLeftDisplayPanel.setLayout(new BoxLayout(topLeftDisplayPanel, BoxLayout.X_AXIS));
        topLeftDisplayPanel.add(genresPanel);
        topLeftDisplayPanel.add(countryPanel);
        topLeftDisplayPanel.add(filmingCountryPanel);

        return topLeftDisplayPanel;
    }


    private JPanel createTopRightPanel() {
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.X_AXIS));
        topRightPanel.add(createTopRight1Panel());
        topRightPanel.add(createTopRight2Panel());

        return topRightPanel;
    }

    private JPanel createTopRight1Panel() {
        JPanel topRight1Panel = new JPanel();
        topRight1Panel.setLayout(new BoxLayout(topRight1Panel, BoxLayout.Y_AXIS));

        JLabel ratingLable = new JLabel("Critics' Rating");
        JLabel yearLable = new JLabel("Movie Year");

        topRight1Panel.add(ratingLable);
        topRight1Panel.add(ratingPanel);
        topRight1Panel.add(Box.createVerticalStrut(70));
        topRight1Panel.add(reviewsPanel);
        topRight1Panel.add(Box.createVerticalStrut(60));
        topRight1Panel.add(yearLable);
        topRight1Panel.add(yearPanel);
        topRight1Panel.add(Box.createVerticalStrut(40));


        return topRight1Panel;
    }

    private JPanel createTopRight2Panel() {
        JPanel topRight2Panel = new JPanel();
        topRight2Panel.setLayout(new BoxLayout(topRight2Panel, BoxLayout.Y_AXIS));


        topRight2Panel.add(tagPanel);
        topRight2Panel.add(Box.createVerticalStrut(20));
        topRight2Panel.add(tagWeightPanel);
        topRight2Panel.add(Box.createVerticalStrut(40));

        return topRight2Panel;


    }



    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        bottomPanel.add(queryPanel);
        bottomPanel.add(resultPanel);
        bottomPanel.add(detailsPanel);

        return bottomPanel;
    }





    private JPanel createGenresPanel() {
        JPanel genresPanel = new JPanel();
        genresPanel.setLayout(new BoxLayout(genresPanel, BoxLayout.Y_AXIS));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        JScrollPane genresScrollPanel = new JScrollPane(checkBoxPanel);
        genresScrollPanel.setPreferredSize(new Dimension(180,200));
        genresScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        genresScrollPanel.setAlignmentY(Component.TOP_ALIGNMENT);


        // add two components into genresPanel
        JLabel genresLable = new JLabel("Genres");
        genresPanel.add(genresLable);
        genresPanel.add(genresScrollPanel);

        ArrayList<String> genres = getGenres();
        for (String genre : genres) {
            JCheckBox genreBox = new JCheckBox(genre);
            genreBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (genreBox.isSelected()) {
                        selectedGenres.add(genreBox.getText());
                    } else {
                        selectedGenres.remove(genreBox.getText());
                    }
                    System.out.println("selectedGenres: " + selectedGenres);
                }
            });
            checkBoxPanel.add(genreBox);
        }

        return genresPanel;
    }


    private JPanel createCountryPanel() {
        JPanel countryPanel = new JPanel();
        countryPanel.setLayout(new BoxLayout(countryPanel, BoxLayout.Y_AXIS));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        JScrollPane countryScrollPanel = new JScrollPane(checkBoxPanel);
        countryScrollPanel.setPreferredSize(new Dimension(150,200));
        countryScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        countryScrollPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel countryLable = new JLabel("Country");
        JButton button = new JButton("generate");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxPanel.removeAll();
                selectedCountry.clear();
                ArrayList<String> countries = getCountries();
                int count = countries.size();
                for (String country : countries) {
                    JCheckBox countryBox = new JCheckBox(country);
                    countryBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (countryBox.isSelected()) {
                                selectedCountry.add(countryBox.getText());
                            } else {
                                selectedCountry.remove(countryBox.getText());
                            }
                            System.out.println("selectedCountry: " + selectedCountry);
                        }
                    });
                    checkBoxPanel.add(countryBox);
                }
                countryLable.setText("Country(*" + count + ")");
                countryScrollPanel.validate();
                countryScrollPanel.repaint();
            }
        });

        // add three components into panel

        countryPanel.add(countryLable);
        countryPanel.add(countryScrollPanel);
        countryPanel.add(button);

        return countryPanel;
    }


    private JPanel createFilmingCountryPanel() {
        JPanel filmingCountryPanel = new JPanel();
        filmingCountryPanel.setLayout(new BoxLayout(filmingCountryPanel, BoxLayout.Y_AXIS));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        JScrollPane filmingCountryScrollPanel = new JScrollPane(checkBoxPanel);
        filmingCountryScrollPanel.setPreferredSize(new Dimension(60,200));
        filmingCountryScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        filmingCountryScrollPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel filmingLable = new JLabel("Filming Location Country");
        JButton button = new JButton("generate");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxPanel.removeAll();
                selectedFilmingLocation.clear();
                ArrayList<String> locations = getLocations();
                int count = locations.size();
                for (String location : locations) {
                    JCheckBox locationBox = new JCheckBox(location);
                    locationBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (locationBox.isSelected()) {
                                selectedFilmingLocation.add(locationBox.getText());
                            } else {
                                selectedFilmingLocation.remove(locationBox.getText());
                            }
                            System.out.println("selectedFilmingLocation: " + selectedFilmingLocation);
                        }
                    });
                    checkBoxPanel.add(locationBox);
                }
                filmingLable.setText("Filming Location Country(*" + count + ")");
                filmingCountryScrollPanel.validate();
                filmingCountryScrollPanel.repaint();
            }
        });

        // add three components into panel
        filmingCountryPanel.add(filmingLable);
        filmingCountryPanel.add(filmingCountryScrollPanel);
        filmingCountryPanel.add(button);

        return filmingCountryPanel;
    }

    private JPanel createSearchOptionPanel() {
        JPanel searchOptionPanel = new JPanel();
        searchOptionPanel.setLayout(new BoxLayout(searchOptionPanel, BoxLayout.X_AXIS));

        JLabel selectName = new JLabel("Search Between Attributes' Values:");
        JComboBox andOr = new JComboBox();
        andOr.addItem("AND");
        andOr.addItem("OR");
        andOr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOption = (String) andOr.getSelectedItem();
                System.out.println("Search Option: " + searchOption);
            }
        });

        searchOptionPanel.add(Box.createGlue());
        searchOptionPanel.add(selectName);
        searchOptionPanel.add(andOr);
        searchOptionPanel.add(Box.createGlue());

        return searchOptionPanel;
    }


    private JPanel createRatingPanel() {
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
        JPanel ratingPanelTop = new JPanel();
        ratingPanelTop.setLayout(new BoxLayout(ratingPanelTop, BoxLayout.X_AXIS));
        JPanel ratingPanelBottom = new JPanel();
        ratingPanelBottom.setLayout(new BoxLayout(ratingPanelBottom, BoxLayout.X_AXIS));
        ratingPanel.add(ratingPanelTop);
        ratingPanel.add(ratingPanelBottom);

        JComboBox rating = new JComboBox();
        rating.addItem("=");
        rating.addItem(">");
        rating.addItem(">=");
        rating.addItem("<");
        rating.addItem("<=");
        rating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ratingRelation = (String) rating.getSelectedItem();
                System.out.println("ratingRelation: " + ratingRelation);
            }
        });

        JTextField ratingValueText = new JTextField();
        ratingValueText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ratingValue = ratingValueText.getText().trim();
                System.out.println("ratingValue: " + ratingValue);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ratingValue = ratingValueText.getText().trim();
                System.out.println("ratingValue: " + ratingValue);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ratingValue = ratingValueText.getText().trim();
                System.out.println("ratingValue: " + ratingValue);
            }
        });

        ratingPanelTop.add(Box.createHorizontalStrut(50));
        ratingPanelTop.add(new JLabel("Rating: "));
        ratingPanelTop.add(rating);
        ratingPanelTop.add(Box.createHorizontalStrut(50));

        ratingPanelBottom.add(Box.createHorizontalStrut(50));
        ratingPanelBottom.add(new JLabel("Value: "));
        ratingPanelBottom.add(ratingValueText);
        ratingPanelBottom.add(Box.createHorizontalStrut(50));

        return ratingPanel;
    }


    private JPanel createReviewsPanel() {
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        JPanel reviewsPanelTop = new JPanel();
        reviewsPanelTop.setLayout(new BoxLayout(reviewsPanelTop, BoxLayout.X_AXIS));
        JPanel reviewsPanelBottom = new JPanel();
        reviewsPanelBottom.setLayout(new BoxLayout(reviewsPanelBottom, BoxLayout.X_AXIS));
        reviewsPanel.add(reviewsPanelTop);
        reviewsPanel.add(reviewsPanelBottom);

        JComboBox reviewsNum = new JComboBox();
        reviewsNum.addItem("=");
        reviewsNum.addItem(">");
        reviewsNum.addItem(">=");
        reviewsNum.addItem("<");
        reviewsNum.addItem("<=");
        reviewsNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numReviewsRelation = (String) reviewsNum.getSelectedItem();
                System.out.println("numReviewsRelation: " + numReviewsRelation);
            }
        });

        JTextField reviewsNumValueText = new JTextField();
        reviewsNumValueText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                numReviewsValue = reviewsNumValueText.getText().trim();
                System.out.println("numReviewsValue: " + numReviewsValue);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                numReviewsValue = reviewsNumValueText.getText().trim();
                System.out.println("numReviewsValue: " + numReviewsValue);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                numReviewsValue = reviewsNumValueText.getText().trim();
                System.out.println("numReviewsValue: " + numReviewsValue);
            }
        });

        reviewsPanelTop.add(Box.createHorizontalStrut(50));
        reviewsPanelTop.add(new JLabel("Num of reviews: "));
        reviewsPanelTop.add(reviewsNum);
        reviewsPanelTop.add(Box.createHorizontalStrut(50));

        reviewsPanelBottom.add(Box.createHorizontalStrut(50));
        reviewsPanelBottom.add(new JLabel("Value: "));
        reviewsPanelBottom.add(reviewsNumValueText);
        reviewsPanelBottom.add(Box.createHorizontalStrut(50));


        return reviewsPanel;
    }



    private JPanel createYearPanel() {
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.Y_AXIS));
        JPanel yearPanelTop = new JPanel();
        yearPanelTop.setLayout(new BoxLayout(yearPanelTop, BoxLayout.X_AXIS));
        JPanel yearPanelBottom = new JPanel();
        yearPanelBottom.setLayout(new BoxLayout(yearPanelBottom, BoxLayout.X_AXIS));
        yearPanel.add(yearPanelTop);
        yearPanel.add(yearPanelBottom);

        JTextField fromText = new JTextField();
        JTextField toText = new JTextField();
        fromText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                yearFrom = fromText.getText().trim();
                System.out.println("yearFrom: " + yearFrom);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                yearFrom = fromText.getText().trim();
                System.out.println("yearFrom: " + yearFrom);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                yearFrom = fromText.getText().trim();
                System.out.println("yearFrom: " + yearFrom);
            }
        });
        toText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                yearTo = toText.getText().trim();
                System.out.println("yearTo: " + yearTo);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                yearTo = toText.getText().trim();
                System.out.println("yearTo: " + yearTo);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                yearTo = toText.getText().trim();
                System.out.println("yearTo: " + yearTo);
            }
        });

        yearPanelTop.add(Box.createHorizontalStrut(50));
        yearPanelTop.add(new JLabel("from: "));
        yearPanelTop.add(fromText);
        yearPanelTop.add(Box.createHorizontalStrut(50));

        yearPanelBottom.add(Box.createHorizontalStrut(50));
        yearPanelBottom.add(new JLabel("to: "));
        yearPanelBottom.add(toText);
        yearPanelBottom.add(Box.createHorizontalStrut(50));

        return yearPanel;
    }


    private JPanel createTagPanel() {
        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.Y_AXIS));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        JScrollPane tagListScroller = new JScrollPane(checkBoxPanel);
        tagListScroller.setPreferredSize(new Dimension(240,200));
        tagListScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        tagListScroller.setAlignmentY(Component.TOP_ALIGNMENT );

        JLabel tagLable = new JLabel("Movie Tag Values");
        JButton button = new JButton("generate");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxPanel.removeAll();
                selectedTags.clear();
                ArrayList<String> tags = getTags();
                int count = tags.size();
                for (String tag : tags) {
                    JCheckBox tagBox = new JCheckBox(tag);
                    tagBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (tagBox.isSelected()) {
                                selectedTags.add(tagBox.getText());
                            } else {
                                selectedTags.remove(tagBox.getText());
                            }
                            System.out.println("selectedTags: " + selectedTags);
                        }
                    });
                    checkBoxPanel.add(tagBox);
                }
                tagLable.setText("Movie Tag Values(*" + count + ")");
                tagListScroller.validate();
                tagListScroller.repaint();
            }
        });


        tagPanel.add(tagLable);
        tagPanel.add(tagListScroller);
        tagPanel.add(button);

        return tagPanel;
    }

    private JPanel createTagWeightPanel() {
        JPanel tagWeightPanel = new JPanel();
        tagWeightPanel.setLayout(new BoxLayout(tagWeightPanel, BoxLayout.Y_AXIS));
        JPanel tagWeightPanelTop = new JPanel();
        tagWeightPanelTop.setLayout(new BoxLayout(tagWeightPanelTop, BoxLayout.X_AXIS));
        JPanel tagWeightPanelBottom = new JPanel();
        tagWeightPanelBottom.setLayout(new BoxLayout(tagWeightPanelBottom, BoxLayout.X_AXIS));
        tagWeightPanel.add(tagWeightPanelTop);
        tagWeightPanel.add(tagWeightPanelBottom);

        JComboBox tagWeight = new JComboBox();
        tagWeight.addItem("=");
        tagWeight.addItem(">");
        tagWeight.addItem(">=");
        tagWeight.addItem("<");
        tagWeight.addItem("<=");
        tagWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tagWeightRelation = (String) tagWeight.getSelectedItem();
                System.out.println("tagWeightRelation: " + tagWeightRelation);
            }
        });

        JTextField tagWeightText = new JTextField();
        tagWeightText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tagWeightValue = tagWeightText.getText().trim();
                System.out.println("tagWeightValue: " + tagWeightValue);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tagWeightValue = tagWeightText.getText().trim();
                System.out.println("tagWeightValue: " + tagWeightValue);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tagWeightValue = tagWeightText.getText().trim();
                System.out.println("tagWeightValue: " + tagWeightValue);
            }
        });

        tagWeightPanelTop.add(Box.createHorizontalStrut(50));
        tagWeightPanelTop.add(new JLabel("Tag Weight: "));
        tagWeightPanelTop.add(tagWeight);
        tagWeightPanelTop.add(Box.createHorizontalStrut(50));

        tagWeightPanelBottom.add(Box.createHorizontalStrut(50));
        tagWeightPanelBottom.add(new JLabel("Value: "));
        tagWeightPanelBottom.add(tagWeightText);
        tagWeightPanelBottom.add(Box.createHorizontalStrut(50));

        return tagWeightPanel;
    }


    private JPanel createQueryPanel() {
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));

        JTextArea query=new JTextArea("",7,30);
        query.setLineWrap(true);

        JScrollPane jsp = new JScrollPane(query);
        jsp.setPreferredSize(new Dimension(400, 400));
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        jsp.setAlignmentY(Component.TOP_ALIGNMENT );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton button1 = new JButton("Generate Query");
        JButton button2 = new JButton("Execute Query");
        buttonPanel.add(button1);
        buttonPanel.add(Box.createRigidArea(new Dimension(120,50)));
        buttonPanel.add(button2);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query.removeAll();
                String sql = generateQuery();
                query.setText(sql);
                query.append(";");
                jsp.validate();
                jsp.repaint();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = generateQuery();
                resultListModel.removeAllElements();
                Map<String, String> idToTitle = getTitles(sql);
                int count = idToTitle.size();

                for (Map.Entry<String, String> entry : idToTitle.entrySet()) {
                    resultListModel.addElement(entry.getKey() + ". " + entry.getValue());
                }

                resultLable.setText("Result(*" + count + ")");
                resultListScroller.validate();
                resultListScroller.repaint();
            }
        });

        // add components into queryPanel
        queryPanel.add(new JLabel("Query Statement"));
        queryPanel.add(Box.createVerticalStrut(5));
        queryPanel.add(jsp);
        queryPanel.add(buttonPanel);

        return queryPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        resultLable = new JLabel("Result");
        JList resultList = new JList(resultListModel);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.setVisibleRowCount(5);
        resultList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selected = (String) resultList.getSelectedValue();
                System.out.println("selected: " + selected);
                if (selected == null) {
                    return;
                }
                String id = selected.split("\\.")[0];
                System.out.println("id: " + id);
                String details = getDetails(id);
                detailsLable.setText(details);

                detailsScroller.validate();
                detailsScroller.repaint();
            }
        });


        resultListScroller = new JScrollPane(resultList);
        resultListScroller.setPreferredSize(new Dimension(250,360));
        resultListScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        resultListScroller.setAlignmentY(Component.TOP_ALIGNMENT );

        resultPanel.add(resultLable);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(resultListScroller);

        return resultPanel;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JPanel helper = new JPanel(new BorderLayout());
        helper.setBackground(Color.WHITE);
        helper.add(detailsLable);
        detailsScroller = new JScrollPane(helper);
        detailsScroller.setPreferredSize(new Dimension(450,360));
        detailsScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        detailsScroller.setAlignmentY(Component.TOP_ALIGNMENT);

        detailsPanel.add(new JLabel("Details"));
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(detailsScroller);

        return detailsPanel;
    }



    private ArrayList<String> getGenres() {
        String sql = "SELECT DISTINCT genre FROM MOVIE_GENRES";
        ArrayList<String> result = fetchColFromDB(sql,"GENRE");
        return result;
    }

    private ArrayList<String> getCountries() {

        String baseSql = "SELECT DISTINCT C.COUNTRY FROM movie_genres G, movie_countries C " +
                "WHERE G.movieID = C.movieID AND ";
        String sql = "";

        sql = generateSql(sql, baseSql, "GENRE", selectedGenres);

        System.out.println("Country SQL: " + sql);
        ArrayList<String> result = fetchColFromDB(sql, "COUNTRY");
        return result;
    }

    private ArrayList<String> getLocations() {

        String baseSql = "SELECT DISTINCT L.LOCATION1 FROM movie_genres G, movie_countries C, movie_locations L " +
                        "WHERE G.movieID = C.Movieid AND C.Movieid = L.movieID AND ";
        String sql = "";

        sql = generateSql(sql, baseSql, "GENRE", selectedGenres);
        if (!selectedCountry.isEmpty()) {
            sql = generateSql(sql, baseSql, "COUNTRY", selectedCountry);
        }

        System.out.println("Location SQL: " + sql);
        ArrayList<String> result = fetchColFromDB(sql, "LOCATION1");
        return result;
    }


    private ArrayList<String> getTags() {

        String baseSql = "SELECT T.value FROM tag T WHERE T.id IN (";
        String partSql = "SELECT MT.tagID FROM movie M, movie_genres G, movie_countries C, " +
                        "movie_locations L, movie_tags MT " +
                        "WHERE M.id = G.movieID AND G.movieID = C.movieID AND C.movieID = L.movieID " +
                        "AND L.movieID = MT.movieID AND ";
        String sql = "";
        sql += baseSql;

        sql = generateSql(sql, partSql, "GENRE", selectedGenres);
        if (!selectedCountry.isEmpty()) {
            sql = generateSql(sql, partSql, "COUNTRY", selectedCountry);
        }
        if (!selectedFilmingLocation.isEmpty()) {
            sql = generateSql(sql, partSql, "LOCATION1", selectedFilmingLocation);
        }
        if (!ratingValue.equals("")) {
            sql = generateSql(sql, partSql, "rtAllCriticsRating", ratingRelation, ratingValue);
        }
        if (!numReviewsValue.equals("")) {
            sql = generateSql(sql, partSql, "rtAllCriticsNumReviews", numReviewsRelation, numReviewsValue);
        }
        if (!yearFrom.equals("") && !yearTo.equals("")) {
            sql = sql + " INTERSECT (";
            sql += partSql + "year" + " >= " + yearFrom + " AND " + "year" + " <= " + yearTo;
            sql = sql + ")";
        }
        if (!tagWeightValue.equals("")) {
            sql = generateSql(sql, partSql, "tagWeight", tagWeightRelation, tagWeightValue);
        }


        sql += ") ORDER BY T.value";

        System.out.println("Tag SQL: " + sql);
        ArrayList<String> result = fetchColFromDB(sql, "value");
        return result;
    }

    private String generateQuery() {
        String baseSql = "SELECT M.id, M.title FROM movie M WHERE M.id IN(";
        String partSql = "SELECT M.ID FROM movie M, movie_genres G, movie_countries C, movie_locations L " +
                "WHERE M.id = G.movieID AND G.movieID = C.movieID AND C.movieID = L.movieID " +
                "AND ";
        if (!selectedTags.isEmpty() || tagWeightValue != "") {
            partSql = "SELECT M.ID FROM movie M, movie_genres G, movie_countries C, movie_locations L, movie_tags MT, tag T " +
                "WHERE M.id = G.movieID AND G.movieID = C.movieID AND C.movieID = L.movieID " +
                "AND L.movieID = MT.movieID AND MT.tagID = T.id " +
                "AND ";
        }
        String sql = "";
        sql += baseSql;

        sql = generateSql(sql, partSql, "GENRE", selectedGenres);
        if (!selectedCountry.isEmpty()) {
            sql = generateSql(sql, partSql, "COUNTRY", selectedCountry);
        }
        if (!selectedFilmingLocation.isEmpty()) {
            sql = generateSql(sql, partSql, "LOCATION1", selectedFilmingLocation);
        }
        if (!ratingValue.equals("")) {
            sql = generateSql(sql, partSql, "rtAllCriticsRating", ratingRelation, ratingValue);
        }
        if (!numReviewsValue.equals("")) {
            sql = generateSql(sql, partSql, "rtAllCriticsNumReviews", numReviewsRelation, numReviewsValue);
        }
        if (!yearFrom.equals("") && !yearTo.equals("")) {
            sql = sql + " INTERSECT (";
            sql += partSql + "year" + " >= " + yearFrom + " AND " + "year" + " <= " + yearTo;
            sql = sql + ")";
        }
        if (!selectedTags.isEmpty()) {
            sql = generateSql(sql, partSql, "T.value", selectedTags);
        }
        if (!tagWeightValue.equals("")) {
            StringBuilder sb = new StringBuilder(sql);
            sb.insert(sql.length() - 1, " AND MT.tagWeight" + tagWeightRelation + tagWeightValue);
//            sql = generateSql(sql, partSql, "tagWeight", tagWeightRelation, tagWeightValue);
            sql = sb.toString();
        }


        sql += ")";

        System.out.println("Query SQL: " + sql);
        return sql;
    }


    private String getDetails(String id) {
        String sqlOfMovie = "SELECT title, year, rtAllCriticsRating, rtTopCriticsRating, " +
                        "rtAudienceRating, rtAllCriticsNumReviews, rtTopCriticsNumReviews, " +
                        "rtAudienceNumRatings FROM MOVIE WHERE id = " + id;

        String sqlOfGenre = "SELECT genre FROM MOVIE_GENRES WHERE movieid = " + id;
        String sqlOfCountry = "SELECT country FROM MOVIE_COUNTRIES WHERE movieid = " + id;


        String title = fetchColFromDB(sqlOfMovie, "title").get(0);
        String year = fetchColFromDB(sqlOfMovie, "year").get(0);
        String rtAllRating = fetchColFromDB(sqlOfMovie, "rtAllCriticsRating").get(0);
        String rtTopRating = fetchColFromDB(sqlOfMovie, "rtTopCriticsRating").get(0);
        String rtAudienceRating = fetchColFromDB(sqlOfMovie, "rtAudienceRating").get(0);
        String rtAllNumReviews = fetchColFromDB(sqlOfMovie, "rtAllCriticsNumReviews").get(0);
        String rtTopNumReviews = fetchColFromDB(sqlOfMovie, "rtTopCriticsNumReviews").get(0);
        String rtAudienceNumRatings = fetchColFromDB(sqlOfMovie, "rtAudienceNumRatings").get(0);

        ArrayList<String> genres = fetchColFromDB(sqlOfGenre, "genre");
        String country = fetchColFromDB(sqlOfCountry, "country").get(0);
        ArrayList<String> locations = getAllLocations(id);

        String details =  "<html><body>ID: " + id + "<br>" +
                            "title: " + title + "<br>" +
                            "country: " + country + "<br>" +
                            "year: " + year + "<br>" +
                            "genre: ";
        for (String genre : genres) {
            details += genre + ", ";
        }
        details += "<br>";
        for (int i = 0; i < locations.size(); i++) {
            details += "location(" + (i+1) + "): " + locations.get(i) + "<br>";
        }
        details += "Rotten Tomato All Critics rating: " + rtAllRating + "<br>";
        details += "Rotten Tomato Top critics rating: " + rtTopRating + "<br>";
        details += "Rotten Tomato Audience rating: " + rtAudienceRating + "<br>";
        details += "Rotten Tomato All Critics number of reviews: " + rtAllNumReviews + "<br>";
        details += "Rotten Tomato Top Critics number of reviews: " + rtTopNumReviews + "<br>";
        details += "Rotten Tomato Audience number of ratings: " + rtAudienceNumRatings + "<br>";


        details += "</body><html>";
        return details;
    }





    private String generateSql(String sql, String baseSql, String colLable, ArrayList<String> list) {
        if (!colLable.equals("GENRE")) {
            sql = sql + " INTERSECT (";
        }

        if (searchOption.equals("AND")) {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    if (list.get(i).equals("")) {
                        sql += baseSql + colLable + " is null";
                        break;
                    }
                    sql += baseSql + colLable + " = '" + list.get(i) + "'";
                    break;
                }
                if (list.get(i).equals("")) {
                    sql += baseSql + colLable + " is null " + "INTERSECT ";
                    continue;
                }
                if (colLable.equals("T.value") && tagWeightValue != "") {
                    sql += baseSql + colLable + " = '" + list.get(i) + "' " + " AND MT.tagWeight "
                            + tagWeightRelation + tagWeightValue + " INTERSECT ";
                    continue;
                }
                sql += baseSql + colLable + " = '" + list.get(i) + "' " + "INTERSECT ";
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    if (list.get(i).equals("")) {
                        sql += baseSql + colLable + " is null";
                        break;
                    }
                    sql += baseSql + colLable + " = '" + list.get(i) + "'";
                    break;
                }
                if (list.get(i).equals("")) {
                    sql += baseSql + colLable + " is null " + "UNION ";
                    continue;
                }
                if (colLable.equals("T.value") && tagWeightValue != "") {
                    sql += baseSql + colLable + " = '" + list.get(i) + "' " + " AND MT.tagWeight "
                            + tagWeightRelation + tagWeightValue + " UNION ";
                    continue;
                }
                sql += baseSql + colLable + " = '" + list.get(i) + "' " + "UNION ";
            }
        }

        if (!colLable.equals("GENRE")) {
            sql = sql + ")";
        }

        return sql;
    }

    private String generateSql(String sql, String baseSql, String colLable, String relation, String value) {
        sql = sql + " INTERSECT (";
        sql += baseSql + colLable + " " + relation + " " + value;
        sql = sql + ")";

        return sql;
    }

    private ArrayList<String> getAllLocations(String id) {
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT location1, location2, location3, location4 " +
                "FROM MOVIE_LOCATIONS WHERE movieid = " + id;
        ResultSet rs = null;
        con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                StringBuilder location = new StringBuilder();
                if (rs.getString("location1") != null) {
                    location.append(rs.getString("location1")).append(" ");
                }
                if (rs.getString("location2") != null) {
                    location.append(rs.getString("location2")).append(" ");
                }
                if (rs.getString("location3") != null) {
                    location.append(rs.getString("location3")).append(" ");
                }
                if (rs.getString("location4") != null) {
                    location.append(rs.getString("location4"));
                }

                result.add(location.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Map<String, String> getTitles(String sql) {
        Map<String, String> result = new HashMap<>();
        ResultSet rs = null;
        con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                result.put(rs.getString("id"), rs.getString("title"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private ArrayList<String> fetchColFromDB(String sql, String colLable) {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = null;
        con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                result.add(rs.getString(colLable));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}

