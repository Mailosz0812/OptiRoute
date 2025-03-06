package org.locations.optiroute.repositories.impl;

import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.repositories.TSPRepository;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TSPRepositoryImpl implements TSPRepository {
    @Override
    public List<AddressEntity> christofidesAlgorithm(Double[][] distanceMatrix,List<AddressEntity>entityList) {
        return List.of();
    }

    @Override
    public List<AddressEntity> solveLinearWithMTZModel(Double[][] distanceMatrix, List<AddressEntity> entityList) {
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        int n = entityList.size();
        AddressEntity[] solvedAddressEntity = new AddressEntity[n];
        solvedAddressEntity[0] = entityList.get(0);
        Variable[][] variables = new Variable[n][n];
        Variable[] u = new Variable[n];
        initializeVariables(distanceMatrix, n, variables, model,u);
        addInOutExpression(n, model, variables);

        //MTZ model Constraints
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (i != j) {
                    model.addExpression("MTZ[" + i + "][" + j+"]")
                            .set(u[i], 1)
                            .set(u[j], -1)
                            .set(variables[i][j], n - 1)
                            .upper(n - 2);
                }
            }
        }

        Optimisation.Result result = model.minimise();
        System.out.println(result);
        for(int i = 1; i < n;i++){
            solvedAddressEntity[u[i].getValue().intValue()] = entityList.get(i);
        }
        for (AddressEntity addressEntity : entityList) {
            System.out.println(addressEntity);
        }
        System.out.println(" ");
        for (AddressEntity addressEntity : solvedAddressEntity) {
            System.out.println(addressEntity);
        }
        return Arrays.asList(solvedAddressEntity);

    }


    private void addInOutExpression(int n, ExpressionsBasedModel model, Variable[][] variables) {
        for (int i = 0; i < n; i++) {
            Expression outDegree = model.addExpression("Out_" + i).level(1);
            Expression inDegree = model.addExpression("In_" + i).level(1);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    outDegree.set(variables[i][j], 1);
                    inDegree.set(variables[j][i], 1);
                }
            }
        }
    }

    private void initializeVariables(Double[][] distanceMatrix, int n, Variable[][] variables, ExpressionsBasedModel model,Variable[] u) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    variables[i][j] = model.addVariable()
                            .weight(distanceMatrix[i][j])
                            .binary();
                }
            }
        }
        for (int i = 1; i < n; i++) {
            u[i] = model.addVariable()
                    .lower(1)
                    .upper(n - 1)
                    .integer();
        }
    }

}

