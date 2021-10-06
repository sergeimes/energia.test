package ee.energia.test.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "basket")
public class Basket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "public.basketseq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "session", nullable = false)
    private String session;

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER)
    private List<BasketItem> items;

    @Column(name = "created")
    @CreatedDate
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
