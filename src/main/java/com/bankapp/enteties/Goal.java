package com.bankapp.enteties;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String description;

    @Column(name = "goal_amount")
    private Long goalAmount;

    @Column(name = "current_amount")
    private Long currentAmount;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(Long goalAmount) {
        this.goalAmount = goalAmount;
    }

    public Long getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
}
//CREATE TABLE IF NOT EXISTS goals
//(
//    id SERIAL PRIMARY KEY,
//    user_id  INT  REFERENCES user_accounts(id) ON DELETE CASCADE,
//    description VARCHAR,
//    goalAmount DECIMAL(10, 2),
//    currentAmount DECIMAL(10, 2),
//    date_added DATE,
//    date_end DATE
//);