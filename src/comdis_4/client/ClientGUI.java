/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.client;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author aculledor
 */
public class ClientGUI extends javax.swing.JFrame {
    private final Client client;
    private VInicio vinicio;
    private VError verror;
    private HashMap<String, ArrayList<String>> chatRepo; 
    private ModeloListaStrings mListaConectados;
    private ModeloListaStrings mListaPeticiones;

    /**
     * Creates new form ClientGUI
     * @param client
     */
    public ClientGUI(Client client) {
        initComponents();
        this.client = client;
        this.chatRepo = new HashMap<>();
    }
    
    public void start(){
        this.vinicio = new VInicio(this, true);
        this.vinicio.pack();
        this.vinicio.setVisible(true);
    }
    
    public void showError(String message){
        System.out.println(message);
        this.verror = new VError(this, true, message);
        this.verror.pack();
        this.verror.setVisible(true);
    }
    
    public void cerrarInicio(){
        this.vinicio.close();
    }
    
    public void cerrarError(){
        this.verror.close();
    }
    
    public void closeApp(){
        this.vinicio.close();
        System.exit(0);
    }
    
    public void signUp(String name, String password, String server){
        this.client.setNickname(name).setPassword(password).setRegistry(server);
        this.client.signUp();
    }
    
    public void connect(String name, String password, String server){
        this.client.setNickname(name).setPassword(password).setRegistry(server);
        this.client.connect();
    }
    
    public void setInteractiveInit(Boolean bool){
        this.vinicio.setInteractive(bool);
    }
    
    public void clearChatRepo(ArrayList<String> friends){
        this.chatRepo = new HashMap<>();
        ArrayList<String> aux;
        for(String friend : friends){
            aux = new ArrayList<>();
            aux.add("["+friend+"] está conectado\n");
            this.chatRepo.put(friend, aux);
        }
        if (mListaConectados.getSize() > 0) {
            this.friendsList.setSelectedIndex(0);
            this.updateChatPrint();
        }else{
            this.repaint();
        }
    }
    
    public void addFriendToChatRepo(String friend){
        ArrayList<String> aux = new ArrayList<>();
        aux.add("["+friend+"] está conectado\n");
        this.chatRepo.put(friend, aux);
        this.updateChatPrint();
    }
    
    public void removeFriendFromChatRepo(String friend){
        this.chatRepo.remove(friend);
        this.updateChatPrint();
    }
    
    public void updateListaPeticiones(ArrayList<String> requests){
        this.mListaPeticiones = new ModeloListaStrings();
        this.requestList.setModel(mListaPeticiones);
        this.mListaPeticiones.setElementos(requests);
        if (this.mListaConectados.getSize() > 0) {
            this.requestList.setSelectedIndex(0);
        }
        this.repaint();
    }
    
    public void updateChatPrint(){
        this.chatTextArea.setText("");
        if (mListaConectados.getSize() > 0 && friendsList.getSelectedIndex() >= 0) {
            this.chatRepo.get(friendsList.getSelectedValue()).forEach((message) -> {
                this.chatTextArea.append(message);
            });
        }
        this.repaint();
    }
    
    public void updateData(ArrayList<String> friends, ArrayList<String> requests){
        this.mListaConectados = new ModeloListaStrings();
        this.friendsList.setModel(mListaConectados);
        this.mListaConectados.setElementos(friends);
        if (this.mListaConectados.getSize() > 0) {
            this.friendsList.setSelectedIndex(0);
        }
        
        friends.forEach((friend) -> {
            this.addFriendToChatRepo(friend);
        });
        
        this.updateListaPeticiones(requests);
        
        this.nameTag.setText(client.getNickname());
        this.repaint();
    }
    
    public void addOnlineFriend(String friend){
        this.mListaConectados.nuevoElemento(friend);
        this.addFriendToChatRepo(friend);
    }
    
    public void removeOnlineFriend(String friend){
        this.mListaConectados.borrarElemento(friend);
        this.removeFriendFromChatRepo(friend);
    }
    
    public void addFriendRequest(String friend){
        this.mListaPeticiones.nuevoElemento(friend);
    }
    
    public void removeFriendRequest(String friend){
        this.mListaConectados.borrarElemento(friend);
    }
    
    public void receiveMessage(String friend, String message){
        this.chatRepo.get(friend).add("["+friend+"] "+ message + "\n");
        if (friendsList.getSelectedValue().equals(friend))
            this.updateChatPrint();
    }
    
    public void receiveServerMessage(String friend, String message){
        this.serverTXTArea.append("["+friend+"] "+ message+"\n");
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        disconnectBTN = new javax.swing.JButton();
        deleteUserBTN = new javax.swing.JButton();
        nameTag = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        requestList = new javax.swing.JList<>();
        acceptRequestBTN = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        newFriendTXT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sendFriendRequestBTN = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        textToSendTXT = new javax.swing.JTextField();
        sendMessageTXT = new javax.swing.JButton();
        acceptRequestBTN1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        serverTXTArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        disconnectBTN.setBackground(new java.awt.Color(255, 204, 204));
        disconnectBTN.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        disconnectBTN.setForeground(new java.awt.Color(0, 0, 0));
        disconnectBTN.setText("CERRAR SESIÓN");
        disconnectBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBTNActionPerformed(evt);
            }
        });

        deleteUserBTN.setBackground(new java.awt.Color(255, 51, 51));
        deleteUserBTN.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        deleteUserBTN.setForeground(new java.awt.Color(0, 0, 0));
        deleteUserBTN.setText("DARSE DE BAJA");
        deleteUserBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserBTNActionPerformed(evt);
            }
        });

        nameTag.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        nameTag.setForeground(new java.awt.Color(0, 0, 0));
        nameTag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameTag.setText("Nombre");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(deleteUserBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(disconnectBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deleteUserBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                        .addComponent(nameTag))
                    .addComponent(disconnectBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(539, 43));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("AMIGOS ONLINE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel1)
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setPreferredSize(new java.awt.Dimension(532, 43));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("PETICIONES DE AMISTAD");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        requestList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        requestList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(requestList);

        acceptRequestBTN.setBackground(new java.awt.Color(0, 204, 204));
        acceptRequestBTN.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        acceptRequestBTN.setForeground(new java.awt.Color(0, 0, 0));
        acceptRequestBTN.setText("ACEPTAR");
        acceptRequestBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptRequestBTNActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(532, 43));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ENVIAR PETICION DE AMISTAD");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(127, 127, 127))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newFriendTXT.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre del nuevo amigo");

        sendFriendRequestBTN.setBackground(new java.awt.Color(0, 204, 204));
        sendFriendRequestBTN.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        sendFriendRequestBTN.setForeground(new java.awt.Color(0, 0, 0));
        sendFriendRequestBTN.setText("ENVIAR");
        sendFriendRequestBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFriendRequestBTNActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Selecciona amigo online para chatear");

        friendsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        friendsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendsListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(friendsList);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Chat");

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        jScrollPane3.setViewportView(chatTextArea);

        textToSendTXT.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        sendMessageTXT.setBackground(new java.awt.Color(0, 204, 204));
        sendMessageTXT.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        sendMessageTXT.setForeground(new java.awt.Color(0, 0, 0));
        sendMessageTXT.setText("ENVIAR");
        sendMessageTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageTXTActionPerformed(evt);
            }
        });

        acceptRequestBTN1.setBackground(new java.awt.Color(255, 51, 51));
        acceptRequestBTN1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        acceptRequestBTN1.setForeground(new java.awt.Color(0, 0, 0));
        acceptRequestBTN1.setText("RECHAZAR");
        acceptRequestBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptRequestBTN1ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setPreferredSize(new java.awt.Dimension(532, 43));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("MENSAJES DEL SERVER");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(143, 143, 143))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        serverTXTArea.setColumns(20);
        serverTXTArea.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        serverTXTArea.setRows(5);
        jScrollPane4.setViewportView(serverTXTArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(textToSendTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(sendMessageTXT)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(acceptRequestBTN1)
                                    .addComponent(acceptRequestBTN, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(newFriendTXT)
                                .addGap(18, 18, 18)
                                .addComponent(sendFriendRequestBTN))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(sendMessageTXT)
                                    .addComponent(textToSendTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(acceptRequestBTN)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(acceptRequestBTN1))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(newFriendTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sendFriendRequestBTN))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4))))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disconnectBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBTNActionPerformed
        // TODO add your handling code here:
        this.client.disconnect();
    }//GEN-LAST:event_disconnectBTNActionPerformed

    private void acceptRequestBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptRequestBTNActionPerformed
        // TODO add your handling code here:
        if (mListaPeticiones.getSize() > 0 && requestList.getSelectedIndex() >= 0) {
            this.client.acceptFriendRequest(requestList.getSelectedValue());
        }
    }//GEN-LAST:event_acceptRequestBTNActionPerformed

    private void requestListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestListMouseClicked
        // TODO add your handling code here:
        System.out.println(requestList.getSelectedValue());
    }//GEN-LAST:event_requestListMouseClicked

    private void acceptRequestBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptRequestBTN1ActionPerformed
        // TODO add your handling code here:
        if (mListaPeticiones.getSize() > 0 && requestList.getSelectedIndex() >= 0) {
            this.client.rejectFriendRequest(requestList.getSelectedValue());
        }
    }//GEN-LAST:event_acceptRequestBTN1ActionPerformed

    private void friendsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendsListMouseClicked
        // TODO add your handling code here:
        this.updateChatPrint();
    }//GEN-LAST:event_friendsListMouseClicked

    public void confirmSent(){
        this.chatRepo.get(friendsList.getSelectedValue()).add("["+this.client.getNickname()+"] "+this.textToSendTXT.getText()+"\n");
        this.textToSendTXT.setText("");
        this.updateChatPrint();
    }
    
    private void sendMessageTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageTXTActionPerformed
        // TODO add your handling code here:
        if (mListaConectados.getSize() > 0 && friendsList.getSelectedIndex() >= 0) {
            this.client.sendMessage(friendsList.getSelectedValue(), this.textToSendTXT.getText());
        }
        this.textToSendTXT.setText("");
    }//GEN-LAST:event_sendMessageTXTActionPerformed

    private void deleteUserBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserBTNActionPerformed
        // TODO add your handling code here:
        this.client.deleteUser();
    }//GEN-LAST:event_deleteUserBTNActionPerformed

    private void sendFriendRequestBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFriendRequestBTNActionPerformed
        // TODO add your handling code here:
        this.client.sendFriendRequest(this.newFriendTXT.getText());
        this.newFriendTXT.setText("");
    }//GEN-LAST:event_sendFriendRequestBTNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptRequestBTN;
    private javax.swing.JButton acceptRequestBTN1;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JButton deleteUserBTN;
    private javax.swing.JButton disconnectBTN;
    private javax.swing.JList<String> friendsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nameTag;
    private javax.swing.JTextField newFriendTXT;
    private javax.swing.JList<String> requestList;
    private javax.swing.JButton sendFriendRequestBTN;
    private javax.swing.JButton sendMessageTXT;
    private javax.swing.JTextArea serverTXTArea;
    private javax.swing.JTextField textToSendTXT;
    // End of variables declaration//GEN-END:variables
}
