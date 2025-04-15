package com.example.paintapi.paint;

import com.example.paintapi.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paint")
public class Paint {

    public Paint(String name, String type, String category, int red, int green, int blue, int amount, User user) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.amount = amount;
        this.ownUser = user;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private int red;

    @Column(nullable = false)
    private int green;

    @Column(nullable = false)
    private int blue;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(nullable = false)
    private int amount = 0;  // デフォルト0（ml）

    @Column(length = 50)
    private String category;
    
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User ownUser;

    // --- Getter & Setter ---

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getRed() { return red; }

    public void setRed(int red) { this.red = red; }

    public int getGreen() { return green; }

    public void setGreen(int green) { this.green = green; }

    public int getBlue() { return blue; }

    public void setBlue(int blue) { this.blue = blue; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
    
    public User getOownUser() { return ownUser; }

    public void setOownUser(User user) { this.ownUser = user; }

}
