package app.ma.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.ma.compositeKey.ProblemContestKey;
import app.ma.compositeKey.ProblemContestUserKey;
import app.ma.entities.Article;
import app.ma.entities.Problem;
import app.ma.entities.ProblemContest;
import app.ma.entities.ProblemContestUser;
import app.ma.entities.Tag;

@Repository
public interface ProblemContestUserRepository extends CrudRepository<ProblemContestUser, ProblemContestUserKey> {
}